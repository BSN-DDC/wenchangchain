package ai.bianjie.ddc.service;

import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.dto.TxInfo;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEvent;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.*;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;
import sun.security.provider.SecureRandom;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class BaseService {
    private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);

    protected SignEventListener signEventListener;
    private static final ThreadLocal<BigInteger> txNonce = new ThreadLocal<BigInteger>();

    /**
     * Get block information
     *
     * @param blockNumber Block height
     * @return Block information
     * @throws IOException
     */
    public EthBlock.Block getBlockByNumber(BigInteger blockNumber) throws IOException {
        return Web3jUtils.getWeb3j().ethGetBlockByNumber(CommonUtils.getDefaultBlockParameter(blockNumber.toString()), true).send().getBlock();
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
     * Offline maintenance user nonce
     *
     * @param nonce When each account initiates a transaction from the same node,
     *              the nonce value starts counting from 0, and sending a nonce corresponds to adding 1.
     *              The subsequent nonce will be processed only after the previous nonce has been processed.
     */
    public void setNonce(BigInteger nonce) {
        txNonce.set(nonce);
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

        // target contract address
        String contractAddr = contract.getContractAddress();

        BigInteger nonce = txNonce.get();

        // If there is no user nonce in the cache, go to the chain to query
        if ((nonce == null) || (nonce.compareTo(BigInteger.ZERO) == 0)) {
            // Get the caller's transaction count
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(sender, DefaultBlockParameterName.PENDING).sendAsync().get();
            nonce = ethGetTransactionCount.getTransactionCount();
        }

        BigInteger gas = new BigInteger("0");
        org.web3j.protocol.core.methods.request.Transaction transaction = new org.web3j.protocol.core.methods.request.Transaction(sender, nonce, gasPrice, gas, contractAddr, BigInteger.valueOf(0), encodedFunction);
        EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).sendAsync().get();
        Response.Error estimateGasError = ethEstimateGas.getError();
        if (estimateGasError != null) {
            throw new DDCException(estimateGasError.getCode(), estimateGasError.getMessage());
        }

        // Estimated gas is used by default when gasLimit == null
        gasLimit = gasLimit != null && gasLimit.compareTo(gas) != 0 ? gasLimit : Numeric.toBigInt(ethEstimateGas.getResult());

        // An error is reported when the specified gasLimit is too low
        if (gasLimit.compareTo(Numeric.toBigInt(ethEstimateGas.getResult())) < 0) {
            throw new DDCException(ErrorMessage.ERR_GASLIMIT);
        }

        // Generate transaction to be signed
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddr, encodedFunction);

        SignEvent signEvent = new SignEvent(sender, rawTransaction);

        // Call the signature method to get the signed hexString
        String signedMessage = signEventListener.signEvent(signEvent);

        // Send a transaction to the chain
        EthSendTransaction sendTransaction = web3j.ethSendRawTransaction(signedMessage).sendAsync().get();

        // Catch exceptions returned on the chain
        Response.Error error = sendTransaction.getError();
        if (error != null) {
            throw new DDCException(error.getCode(), error.getMessage());
        }

        // Clear the nonce at the end of the transaction
        txNonce.remove();

        // return transaction result
        return sendTransaction;
    }

    /**
     * The platform party or end user generates offline accounts through this method.
     *
     * @return Account, Account address
     */
    public Account createAccount() {
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

    /**
     * The platform party or end user can query the GAS fee balance corresponding to the chain account through this method.
     *
     * @param account Hex format account
     * @return GAS balance
     */
    public BigInteger BalanceOfGas(String account) throws IOException {
        return Web3jUtils.getWeb3j().ethGetBalance(account, DefaultBlockParameterName.LATEST).send().getBalance();
    }

    /**
     * Generate Offline Hash
     *
     * @param contract          Contract instance
     * @param functionName      method name to call
     * @param encodedFunction   Function encoded by RLP serialization
     * @param signEventListener The instance responsible for signing
     * @return txHash, Offline Hash
     */
    public String generateOfflineHash(Contract contract, String functionName, String encodedFunction, SignEventListener signEventListener, String sender) {
        GasProvider gasProvider = new GasProvider();
        BigInteger gasPrice = gasProvider.getGasPrice();
        BigInteger gasLimit = gasProvider.getGasLimit(functionName);
        // target contract address
        String contractAddr = contract.getContractAddress();
        BigInteger nonce = txNonce.get();
        // Generate transaction to be signed
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddr, encodedFunction);
        // Call the signature method to get the signed hexString
        SignEvent signEvent = new SignEvent(sender, rawTransaction);
        String signedMessage = signEventListener.signEvent(signEvent);
        String txHash = Hash.sha3(signedMessage);
        return txHash;
    }

    /***
     * check Sender
     * @param sender
     */
    public void checkSender(String sender) {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.SENDER_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }
    }
}
