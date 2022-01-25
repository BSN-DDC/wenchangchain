package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.dto.BlockEventBean;
import ai.bianjie.ddc.listener.sign;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

class BlockEventServiceTest {

    DDCSdkClient client = new DDCSdkClient.Builder()
            .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
            .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
            .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
            .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
            .setGasLimit("300000")
            .setGasPrice("10000000")
            .setSignEventListener(new sign())
            .init();

    @Test
    void getBlockEvent() throws IOException, InterruptedException, ExecutionException {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        BlockEventService blockEventService = new BlockEventService();
        BlockEventBean blockEvent = blockEventService.getBlockEvent(new BigInteger("3058179"));
    }
}