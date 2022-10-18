package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.dto.BlockEventBean;
import ai.bianjie.ddc.SignEventTest;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.BaseEventResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class BlockEventServiceTest {
    DDCSdkClient client = DDCSdkClientTest.getClient();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        BlockEventService blockEventService = new BlockEventService();
        BlockEventBean blockEvent = blockEventService.getBlockEvent(new BigInteger("3523189"));
        System.out.println(blockEvent);
    }


    @Test
    void getBlockEvent() throws IOException, ExecutionException, InterruptedException {
        BlockEventService blockEventService = new BlockEventService();
        BlockEventBean blockEvent = blockEventService.getBlockEvent(new BigInteger("3523189"));
        System.out.println(blockEvent);
    }

    @Test
    void getEventByHash() throws Exception {
        BlockEventService blockEventService = new BlockEventService();
//        ArrayList<BaseEventResponse> baseEventResponses = blockEventService.analyzeEventsByTxHash("0x376e5325c7beae97165b7ef1e13b672f85ec1c96b73b50c6d2806f06882599e5");
        ArrayList<BaseEventResponse> baseEventResponses = blockEventService.analyzeEventsByTxHash("0x91bb20d66f2c45b51df9d9fdf801e9527ea640c67af61ba99f426e57b58b7175");
        System.out.println(baseEventResponses.toString());
    }
}