package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.dto.TxInfo;
import ai.bianjie.ddc.SignEventTest;
import org.bitcoinj.crypto.MnemonicException;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

class BaseServiceTest {
    DDCSdkClient client = DDCSdkClientTest.getBSNClient();

    BaseService baseService = client.getChargeService();


    @Test
    void getBlockByNumber() throws IOException {
        EthBlock.Block block = baseService.getBlockByNumber(new BigInteger("3058179"));
        System.out.println("--------------------------------------" + block.getHash());
    }

    @Test
    void getTransReceipt() throws ExecutionException, InterruptedException {
        TransactionReceipt transactionReceipt = baseService.getTransReceipt("0x6045220771fafea125d52d9a0565be816b1d5cdfe8f03bd2d511b6bf8671018d");
        System.out.println("tx receipt: " + transactionReceipt);
    }

    @Test
    void getTransByHash() throws IOException {
        TxInfo transaction = baseService.getTransByHash("0x7b470e83b464ebce5f5e9bc0bcc3ac56d1d44651bca600fd524b13d06e49f3cc");
        System.out.println("tx: " + transaction);
    }

    @Test
    void getTransByStatus() throws IOException {
        Boolean status = baseService.getTransByStatus("0x8c13384524226c5e6068bde16909f3c90784cf9ce6ea9dafdf6157b7b1a92b72");
        System.out.println("--------------------------------------" + status);
    }

    @Test
    void getLatestBlockNumber() throws IOException {
        System.out.println(baseService.getLatestBlockNumber());
    }

    @Test
    void AccoutHexToBech32() throws MnemonicException.MnemonicLengthException {
        Account acc = baseService.createAccount();
        System.out.println(baseService.accountHexToBech32(acc.getAddress()));
    }

    @Test
    void AccountBech32ToHex() throws MnemonicException.MnemonicLengthException {
        Account acc = baseService.createAccount();
        System.out.println(acc.getAddress());
        String a = baseService.accountHexToBech32(acc.getAddress());
        System.out.println(baseService.accountBech32ToHex(a));
    }

    @Test
    void createAccount() {
        System.out.println(baseService.createAccount().getAddress());
        System.out.println(baseService.createAccount().getPrivateKey());
        System.out.println(baseService.createAccount().getKeyseed());
    }

    @Test
    void balanceOfGas() throws IOException {
        System.out.println(baseService.BalanceOfGas(DDCSdkClientTest.consumer));
    }
}