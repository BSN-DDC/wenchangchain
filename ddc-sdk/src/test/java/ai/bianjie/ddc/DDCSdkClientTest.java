package ai.bianjie.ddc;

import ai.bianjie.ddc.service.BaseService;
import ai.bianjie.ddc.service.ChargeService;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;


public class DDCSdkClientTest {
    static DDCSdkClient client;

    public static String operator = "0x02CEB40D892061D457E7FA346988D0FF329935DF";
    public static String platform = "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E";
    public static String consumer = "0x32B6034EEBF55CEEF908816BE6709B234A2376D2";

    public static DDCSdkClient getClient() {
        client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29") // 权限代理合约地址
                .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D")    // 计费代理合约地址
                .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870")         // DDC 721代理合约地址
                .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973")        // DDC 1155代理合约地址
                .setOpbCrossChainAddress("0x0b563caa7F2Bd3E9b68C6e421973ddA2dD51f03a")  // OPB 跨链代理合约地址
                .setGasLimit("3000000")
                .setGasPrice("1")
                .setSignEventListener(new SignEventTest())
                .setCredentials("ED43B9686AB520C896BC33A1461BAF163EDBF0DBC4D3199E77793A49B9BB2568")
                .init();
        client.setGatewayUrl("https://opbningxia.bsngate.com:18602/api/[项目ID]/evmrpc");

        return client;
    }

    public static DDCSdkClient getBSNClient() {
        client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29") // 权限代理合约地址
                .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D")    // 计费代理合约地址
                .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870")         // DDC 721代理合约地址
                .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973")        // DDC 1155代理合约地址
                .setOpbCrossChainAddress("0x0b563caa7F2Bd3E9b68C6e421973ddA2dD51f03a")  // OPB 跨链代理合约地址
                .setGasLimit("3000000")
                .setGasPrice("1")
                .setSignEventListener(new SignEventTest())
                .init();
        client.setGatewayUrl("https://opbningxia.bsngate.com:18602/api/[项目ID]/evmrpc");
//        client.setGatewayApiKey("x-api-key");
//        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");

        return client;
    }

    @Test
    void sdkInitTest() throws Exception {
        client = getClient();
        client.setConnectTimeout(20);
        String sender = platform;

        ChargeService chargeService = client.getChargeService();
        BaseService baseService = new BaseService();

        baseService.setNonce(new BigInteger("481"));
        String hash = chargeService.recharge(sender, "0xd55172e02723cec9f0a89dbcdc1675098152ac52", new BigInteger("100"));
        System.out.print(hash);

        baseService.setNonce(new BigInteger("482"));
        String hash1 = chargeService.recharge(sender, "0xd55172e02723cec9f0a89dbcdc1675098152ac52", new BigInteger("10"));
        System.out.print(hash1);

        baseService.setNonce(new BigInteger("483"));
        String hash2 = chargeService.recharge(sender, "0xd55172e02723cec9f0a89dbcdc1675098152ac52", new BigInteger("10"));
        System.out.print(hash2);
    }
}