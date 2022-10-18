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
                .setAuthorityLogicAddress("0x9664F35ccAC9dD38Be831c80c2698c9DD7Ee7c4A")
                .setChargeLogicAddress("0x276076313757029E876B9F6B3D4404941843F8FB")
                .setDDC721Address("0x63edD4A5C49B4caC9306F8FF01c32aDc023934cE")
                .setDDC1155Address("0xE15D6E4B2E710a2ECb1fe0DEB1DF9041181d0ED2")
                .setGasLimit("3000")
                .setGasPrice("1")
                .setSignEventListener(new SignEventTest())
                .setCredentials("ED43B9686AB520C896BC33A1461BAF163EDBF0DBC4D3199E77793A49B9BB2568")
                .init();
        client.setGatewayUrl("http://192.168.150.42:8545");

        return client;
    }

    public static DDCSdkClient getBSNClient() {
        client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xDb391cFC8BCF12312E690131404c675730c5Ce82")
                .setChargeLogicAddress("0x2991Ef144245893e54c82072F138479Aa65Aea5C")
                .setDDC721Address("0x7D95Cb8b8a3d4A353c4d7532c6Fa81cd3c339765")
                .setDDC1155Address("0xe0F2051eEe637C026FcEFE27a3e5AEDD939d3fC5")
                .setGasLimit("300000")
                .setGasPrice("1")
                .setSignEventListener(new SignEventTest())
                .init();
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/IRISnetrest/evmrpc");
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