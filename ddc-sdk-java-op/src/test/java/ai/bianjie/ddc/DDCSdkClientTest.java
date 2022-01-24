package ai.bianjie.ddc;

import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.listener.sign;
import ai.bianjie.ddc.service.BaseService;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

class DDCSdkClientTest {

    @Test
    public void sdkInitTest() throws Exception {
//        DDCSdkClient client = new DDCSdkClient.Builder("https://opbtest.bsngate.com:18602/api/IRISnetrest/evmrpc").authorityLogicAddress("0xdAc50c90b934AdED33b6ADc9f5855ab8a9EFB09a").chargeLogicAddress("0x52403cE9E235Cf013bA2353F0bf47834C98424c7").ddc721Address("0x503f45958F57Da55170B54796F4eD224c9fef9d7").ddc1155Address("0xe7310D2D79c67a3078DBeFA67344c7047AC28708").gasLimit("300000").gasPrice("10000000").init();
//        SignEventListener signEventListener = new sign();
//        client.registerSignListener(signEventListener);
//        String a = client.getChargeService().recharge("918F7F275A6C2D158E5B76F769D3F1678958A334",new BigInteger("10"));
//        System.out.println("================================"+a);
//        BigInteger b = client.getChargeService().setGasLimitCharge("1").balanceOf("918F7F275A6C2D158E5B76F769D3F1678958A334");
//        System.out.println("========================"+b);

        DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
                .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
                .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
                .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
                .setGasLimit("300000")
                .setGasPrice("10000000")
                .setSignEventListener(new sign())
                .init();

        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");

//                client.getChargeService().setFee("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721","0x52403cE9E235Cf013bA2353F0bf47834C98424c7","0x36351c7c", new BigInteger("10"));
//        System.out.println("================================" + a);
        BaseService baseService=new BaseService();
        Account acc = baseService.createAccountHex();
        System.out.println("================================" + acc.getAddress());
        String addHex= baseService.AccountHexToBech32(acc.getAddress());
        System.out.println("================================" + addHex);
//        BigInteger b = client.getChargeService().setGasLimitCharge("1").balanceOf("918F7F275A6C2D158E5B76F769D3F1678958A334");
//        System.out.println("========================"+b);
    }
}