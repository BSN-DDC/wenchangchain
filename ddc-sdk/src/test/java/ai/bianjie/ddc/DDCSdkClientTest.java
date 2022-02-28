package ai.bianjie.ddc;

import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.service.BaseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class DDCSdkClientTest {

    @Test
    void sdkInitTest() throws Exception {

        DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
                .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
                .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
                .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
                .setGasLimit("300000")
                .setGasPrice("1")
                .setSignEventListener(new SignEventTest())
                .init();

        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");

        BaseService baseService = new BaseService();
        baseService.setFuncGasLimit("100000");
        Account acc = baseService.createAccountHex();
        System.out.println("================================" + acc.getAddress());
        System.out.println("================================" + acc.getPrivateKey());
        String addHex = baseService.accountHexToBech32(acc.getAddress());
        System.out.println("================================" + addHex);
    }
}