package ai.bianjie.ddc.service;

import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.dto.txInfo;
import ai.bianjie.ddc.listener.SignEvent;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.Bech32Utils;
import ai.bianjie.ddc.util.CommonUtils;
import ai.bianjie.ddc.util.GasProvider;
import ai.bianjie.ddc.util.Web3jUtils;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.*;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.utils.Strings;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.web3j.crypto.Hash.sha256;

@Slf4j
public class BaseService {
    protected SignEventListener signEventListener;

    /**
     * 获取区块信息
     *
     * @return 区块信息
     */
    public EthBlock.Block getBlockByNumber(BigInteger blockNumber) throws IOException {
        return Web3jUtils.getWeb3j().ethGetBlockByNumber(CommonUtils.getDefaultBlockParamter(blockNumber.toString()), true).send().getBlock();
    }

    /**
     * 获取最新的块高
     */
    public BigInteger getLatestBlockNumber() throws IOException {
        return Web3jUtils.getWeb3j().ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getNumber();
    }

    /**
     * 查询交易回执
     *
     * @param hash 交易哈希
     * @return 交易回执
     * @throws InterruptedException InterruptedException
     */
    public TransactionReceipt getTransReceipt(String hash) throws InterruptedException, ExecutionException {
        TransactionReceipt txReceipt = Web3jUtils.getWeb3j().ethGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt().get();
        return txReceipt;
    }

    /**
     * 查询交易信息
     *
     * @param hash 交易哈希
     * @return 交易信息
     */
    public txInfo getTransByHash(String hash) throws IOException {
        Transaction transaction = Web3jUtils.getWeb3j().ethGetTransactionByHash(hash).send().getTransaction().get();
        return new txInfo(transaction.getHash(), transaction.getNonceRaw(), transaction.getBlockHash(),
                transaction.getBlockNumber().toString(), transaction.getTransactionIndex().toString(),
                transaction.getFrom(), transaction.getTo(), transaction.getValue().toString(),
                transaction.getGasPrice().toString(), transaction.getGas().toString(),
                transaction.getInput(), transaction.getCreates(), transaction.getPublicKey(),
                transaction.getRaw(), transaction.getR(), transaction.getS(), transaction.getV());
    }

    /**
     * 查询交易状态
     *
     * @param hash 交易哈希
     * @return 交易状态
     */
    public Boolean getTransByStatus(String hash) throws ExecutionException, InterruptedException {
        TransactionReceipt txReceipt = Web3jUtils.getWeb3j().ethGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt().get();
        return !Strings.isEmpty(txReceipt.toString());
    }

    /**
     * 初始化gasLimit集合
     *
     * @param gasLimit
     */
    public void setFuncGasLimit(String gasLimit) {
        Map<String, String> map = new HashMap<>();
        ConfigCache.get().setFuncGasLimit(gasLimit);
    }

    /**
     * 签名并发送
     *
     * @param contract          合约实例
     * @param functionName      调用的方法名
     * @param encodedFunction   经过RLP序列化编码的function
     * @param signEventListener 负责签名的实例
     * @return EthSendTransaction 交易的结果
     */
    public EthSendTransaction signAndSend(Contract contract, String functionName, String encodedFunction, SignEventListener signEventListener, String sender) throws Exception {

        Web3j web3j = Web3jUtils.getWeb3j();
        GasProvider gasProvider = new GasProvider();

        BigInteger gasPrice = gasProvider.getGasPrice();
        BigInteger gasLimit = gasProvider.getGasLimit(functionName);

        String contractAddr = contract.getContractAddress();//目标合约地址

        //2. 获取调用者的交易笔数
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(sender, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        //3. 生成待签名的交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddr, encodedFunction);

        SignEvent signEvent = new SignEvent();


        signEvent.setSender(sender);
        signEvent.setRawTransaction(rawTransaction);

        //4. 调用签名方法，获取签名后的hexString
        String hexString_signedMessage = signEventListener.signEvent(signEvent);

        //5. 返回交易结果
        return web3j.ethSendRawTransaction(hexString_signedMessage).sendAsync().get();
    }

    /**
     * 签名并发送
     *
     * @param contract          合约实例
     * @param functionName      调用的方法名
     * @param encodedFunction   经过RLP序列化编码的function
     * @param signEventListener 负责签名的实例
     * @return EthSendTransaction 交易的结果
     */
    public EthSendTransaction Send(Contract contract, String functionName, String encodedFunction, SignEventListener signEventListener, String sender) throws Exception {

        Web3j web3j = Web3jUtils.getWeb3j();
        GasProvider gasProvider = new GasProvider();

        BigInteger gasPrice = gasProvider.getGasPrice();
        BigInteger gasLimit = gasProvider.getGasLimit(functionName);

        String contractAddr = contract.getContractAddress();//目标合约地址

        //2. 获取调用者的交易笔数
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(sender, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        //3. 生成待签名的交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddr, encodedFunction);

        SignEvent signEvent = new SignEvent();


        signEvent.setSender(sender);
        signEvent.setRawTransaction(rawTransaction);

        //4. 调用签名方法，获取签名后的hexString
        String hexString_signedMessage = signEventListener.signEvent(signEvent);

        //5. 返回交易结果
        return web3j.ethSendRawTransaction(hexString_signedMessage).send();
    }

    /**
     * 平台方或终端用户通过该方法进行离线账户生成。
     *
     * @return 返回 Account
     * @throws Exception
     */
    public Account createAccountHex() throws MnemonicException.MnemonicLengthException {
        byte[] initialEntropy = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initialEntropy);
        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair keyPair = ECKeyPair.create(sha256(seed));
        String addr = Keys.getAddress(keyPair);

        return new Account(mnemonic, keyPair.getPublicKey().toString(16), keyPair.getPrivateKey().toString(16), addr);
        //privatekey: "0x"+keyPair.getPrivateKey().toString(16)
        //publickey: keyPair.getPublicKey().toString(16)
    }

    public String AccountHexToBech32(String addr) {
        String hrp = "iaa";
        return Bech32Utils.hexToBech32(hrp, addr);
    }

    public String AccountBech32ToHex(String addr) {
        return Bech32Utils.bech32ToHex(addr);
    }
}
