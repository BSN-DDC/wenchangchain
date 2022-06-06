package ai.bianjie.ddc.service;

import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.dto.TxInfo;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEvent;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.Bech32Utils;
import ai.bianjie.ddc.util.CommonUtils;
import ai.bianjie.ddc.util.GasProvider;
import ai.bianjie.ddc.util.Web3jUtils;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.utils.Strings;
import sun.security.provider.SecureRandom;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.web3j.crypto.Hash.sha256;

@Slf4j
public class BaseService {
    private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);

    protected SignEventListener signEventListener;

    /**
     * Get block information
     *
     * @param blockNumber Block height
     * @return Block information
     * @throws IOException
     */
    public EthBlock.Block getBlockByNumber(BigInteger blockNumber) throws IOException {
        return Web3jUtils.getWeb3j().ethGetBlockByNumber(CommonUtils.getDefaultBlockParamter(blockNumber.toString()), true).send().getBlock();
    }

    /**
     * Get the latest block height
     *
     * @return blockNumber, Block height
     * @throws IOException
     */
    public BigInteger getLatestBlockNumber() throws IOException {
        return Web3jUtils.getWeb3j().ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getNumber();
    }

    /**
     * Query transaction receipt
     *
     * @param hash Transaction hash
     * @return receipt, Transaction receipt
     * @throws InterruptedException
     */
    public TransactionReceipt getTransReceipt(String hash) throws InterruptedException, ExecutionException {
        return Web3jUtils.getWeb3j().ethGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt().get();
    }

    /**
     * Query transaction information
     *
     * @param hash Transaction hash
     * @return TxInfo, Transaction information
     * @throws IOException
     */
    public TxInfo getTransByHash(String hash) throws IOException {
        Transaction transaction = Web3jUtils.getWeb3j().ethGetTransactionByHash(hash).send().getTransaction().get();
        return new TxInfo(transaction);
    }

    /**
     * Query transaction status
     *
     * @param hash Transaction hash
     * @return status, Transaction status
     * @throws ExecutionException
     */
    public Boolean getTransByStatus(String hash) throws IOException {
        return Web3jUtils.getWeb3j().ethGetTransactionReceipt(hash).send().getTransactionReceipt().get().isStatusOK();
    }

    /**
     * Initialize the gasLimit collection
     *
     * @param gasLimit
     */
    public void setFuncGasLimit(String gasLimit) {
        ConfigCache.get().setFuncGasLimit(gasLimit);
    }

    /**
     * Sign and send
     *
     * @param contract          Contract instance
     * @param functionName      method name to call
     * @param encodedFunction   Function encoded by RLP serialization
     * @param signEventListener The instance responsible for signing
     * @return EthSendTransaction, Result of the transaction
     * @throws ExecutionException
     */
    public EthSendTransaction signAndSend(Contract contract, String functionName, String encodedFunction, SignEventListener signEventListener, String sender) throws ExecutionException, InterruptedException {

        Web3j web3j = Web3jUtils.getWeb3j();
        GasProvider gasProvider = new GasProvider();

        BigInteger gasPrice = gasProvider.getGasPrice();
        BigInteger gasLimit = gasProvider.getGasLimit(functionName);

        //目标合约地址
        String contractAddr = contract.getContractAddress();

        // 获取调用者的交易笔数
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(sender, DefaultBlockParameterName.PENDING).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        // 生成待签名的交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddr, encodedFunction);

        SignEvent signEvent = new SignEvent(sender, rawTransaction);

        // 调用签名方法，获取签名后的hexString
        String signedMessage = signEventListener.signEvent(signEvent);

        // 向链上发送交易
        EthSendTransaction sendTransaction = web3j.ethSendRawTransaction(signedMessage).sendAsync().get();
        // 捕获链上返回的异常
        Response.Error error = sendTransaction.getError();
        if (error != null) {
            throw new DDCException(error.getCode(), error.getMessage());
        }
        // 返回交易结果
        return sendTransaction;
    }

    /**
     * The platform party or end user generates offline accounts through this method.
     *
     * @return Account, Account address
     */
    public Account createAccountHex() {
        sun.security.provider.SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.engineNextBytes(entropy);
        List<String> str = null;
        try {
            str = MnemonicCode.INSTANCE.toMnemonic(entropy);
        } catch (MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
        }
        byte[] seed = MnemonicCode.toSeed(str, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        DeterministicKey deterministicKey = deterministicHierarchy.deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
        byte[] bytes = deterministicKey.getPrivKeyBytes();
        ECKeyPair keyPair = ECKeyPair.create(bytes);
        String addr = Keys.getAddress(keyPair.getPublicKey());
        return new Account(str.toString(), keyPair.getPublicKey().toString(16), keyPair.getPrivateKey().toString(16), addr);
    }

    /**
     * The platform party or end user uses this method to convert the HEX format account.
     *
     * @param addr HEX format account
     * @return Bech32 format account
     */
    public String accountHexToBech32(String addr) {
        String hrp = "iaa";
        return Bech32Utils.hexToBech32(hrp, addr);
    }

    /**
     * The platform party or end user uses this method to convert accounts in Bech32 format.
     *
     * @param addr Bech32 format account
     * @return HEX format account
     */
    public String accountBech32ToHex(String addr) {
        return Bech32Utils.bech32ToHex(addr);
    }
}
