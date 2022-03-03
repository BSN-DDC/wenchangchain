package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.SignEventTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class DDC1155ServiceTest {

    DDCSdkClient client = new DDCSdkClient.Builder()
            .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
            .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
            .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
            .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
            .setGasLimit("300000")
            .setGasPrice("1")
            .setSignEventListener(new SignEventTest())
            .init();

    DDC1155Service ddc1155Service = client.getDDC1155Service();
    String sender = "0x953488F7E292A7D6CB0BFF81BA806B82E5FD47A2";

    @Test
    void safeMint() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeMint(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("3"), "222222", data));
    }

    @Test
    void safeMintBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        Multimap<BigInteger, String> ddcInfo = ArrayListMultimap.create();
        ;
        ddcInfo.put(new BigInteger("3"), "12");
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeMintBatch(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334", ddcInfo, data));
    }

    @Test
    void setApprovalForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.setApprovalForAll(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334", true));
    }

    @Test
    void isApprovedForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.isApprovedForAll("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "918F7F275A6C2D158E5B76F769D3F1678958A334"));
    }

    @Test
    void safeTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeTransferFrom(sender,"0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "918F7F275A6C2D158E5B76F769D3F1678958A334",new BigInteger("1"),new BigInteger("11"),data));
    }

    @Test
    void safeBatchTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        Multimap<BigInteger, BigInteger> ddcs = ArrayListMultimap.create();
        ddcs.put(new BigInteger("3"), new BigInteger("3"));
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeBatchTransferFrom(sender,"0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "918F7F275A6C2D158E5B76F769D3F1678958A334",ddcs,data));
    }

    @Test
    void freeze() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.freeze(sender, new BigInteger("12")));
    }

    @Test
    void unFreeze() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.unFreeze(sender, new BigInteger("12")));
    }

    @Test
    void burn() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.burn(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("2")));
    }

    @Test
    void burnBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        List<BigInteger> ddcIds = new ArrayList<>();
        ddcIds.add(BigInteger.valueOf(12));
        System.out.println(ddc1155Service.burnBatch(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334",ddcIds));
    }

    @Test
    void balanceOf() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.balanceOf("918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("2")));
    }

    @Test
    void balanceOfBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        Multimap<String, BigInteger> ddcs = ArrayListMultimap.create();
        ddcs.put("918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("3"));
        ddcs.put("918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("5"));
        List<BigInteger> a = new ArrayList<>(2);
        a = ddc1155Service.balanceOfBatch(ddcs);
        System.out.println(a);
    }

    @Test
    void ddcURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.ddcURI(new BigInteger("3")));
    }

    @Test
    void setURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("903f4f9268ab4e2eac717c7200429776");
        client.setGatewayApiValue("0c1dd14a41b14cfa83048d839a0593ff");
        System.out.println(ddc1155Service.setURI(sender, "918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("3"), "1234"));
    }
}