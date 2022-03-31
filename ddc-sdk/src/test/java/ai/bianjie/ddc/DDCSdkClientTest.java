package ai.bianjie.ddc;

import ai.bianjie.ddc.dto.Account;
import ai.bianjie.ddc.dto.BlockEventBean;
import ai.bianjie.ddc.service.AuthorityService;
import ai.bianjie.ddc.service.BaseService;
import ai.bianjie.ddc.service.BlockEventService;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNull;

class DDCSdkClientTest {

    @Test
    private DDCSdkClient sdkInitTest() throws Exception {

//        DDCSdkClient client = new DDCSdkClient.Builder()
//                .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
//                .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
//                .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
//                .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
//                .setGasLimit("300000")
//                .setGasPrice("10000000")
//                .setSignEventListener(new SignEventTest())
//                .init();
//
//        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
//        client.setGatewayApiKey("x-api-key");
//        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");


        DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
                .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
                .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
                .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
                .setGasLimit("300000")
                .setGasPrice("10000000")
                .setSignEventListener(new SignEventTest())
                .init();

        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
//        client.setGatewayApiKey("x-api-key");
//        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");

        return client;
    }

    @Test
    void test() throws Exception {
        DDCSdkClient client = sdkInitTest();
//        AuthorityService authorityService = client.getAuthorityService();
        BlockEventService bes = new BlockEventService();
        while (true){
            BigInteger latestBlockNumber = bes.getLatestBlockNumber();
            BlockEventBean blockEvent = bes.getBlockEvent(latestBlockNumber);
            System.out.println("-------1--------时间:"+blockEvent.getTimeStamp());
            System.out.println("-------2--------块高:"+latestBlockNumber);
            List list = blockEvent.getList();
            if (list == null || list.isEmpty()){
               System.out.println("list is null");
            }else {
                blockEvent.getList().forEach(res->{
                    String transactionHash = res.log.getTransactionHash();
                    TransactionReceipt receipt = null;
                    try {
                        receipt = bes.getTransReceipt(transactionHash);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (receipt!= null){
                        System.out.println("-------3--------块高:"+receipt.getBlockNumber());
                        System.out.println("-------4--------数据-gasused:"+receipt.getGasUsed());
                    }
                });
            }
            Thread.sleep(1000);
        }
    }
}