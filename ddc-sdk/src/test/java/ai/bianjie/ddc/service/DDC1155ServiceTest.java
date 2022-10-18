package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class DDC1155ServiceTest {

    DDCSdkClient client = DDCSdkClientTest.getClient();

    DDC1155Service ddc1155Service = client.getDDC1155Service();

    String sender = DDCSdkClientTest.platform;
    String privateKey = "0xED43B9686AB520C896BC33A1461BAF163EDBF0DBC4D3199E77793A49B9BB2568";
    DDC1155MetaTransaction metaTransaction = DDC1155MetaTransaction.builder()
            .setChainId(BigInteger.valueOf(1223))
            .setContractAddress("0xE15D6E4B2E710a2ECb1fe0DEB1DF9041181d0ED2")
            .build();

    @Test
    void safeMint() throws Exception {
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeMint(DDCSdkClientTest.consumer, DDCSdkClientTest.consumer, new BigInteger("2"), "222222", data));
    }

    @Test
    void safeMintBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        Multimap<BigInteger, String> ddcInfo = ArrayListMultimap.create();
        ;
        ddcInfo.put(new BigInteger("3"), "12");
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeMintBatch(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", ddcInfo, data));
    }

    @Test
    void setApprovalForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.setApprovalForAll(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", true));
    }

    @Test
    void isApprovedForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.isApprovedForAll("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E"));
    }

    @Test
    void safeTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeTransferFrom(sender, "0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", new BigInteger("1"), new BigInteger("11"), data));
    }

    @Test
    void safeBatchTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        Multimap<BigInteger, BigInteger> ddcs = ArrayListMultimap.create();
        ddcs.put(new BigInteger("3"), new BigInteger("3"));
        byte[] data = {10, 10, 10};
        System.out.println(ddc1155Service.safeBatchTransferFrom(sender, "0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", ddcs, data));
    }

    @Test
    void burn() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.burn(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", new BigInteger("2")));
    }

    @Test
    void burnBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        List<BigInteger> ddcIds = new ArrayList<>();
        ddcIds.add(BigInteger.valueOf(12));
        System.out.println(ddc1155Service.burnBatch(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", ddcIds));
    }

    @Test
    void balanceOf() throws Exception {
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.balanceOf(DDCSdkClientTest.platform, new BigInteger("1")));
    }

    @Test
    void balanceOfBatch() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        Multimap<String, BigInteger> ddcs = ArrayListMultimap.create();
        ddcs.put("0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", new BigInteger("1"));
        List<BigInteger> a = new ArrayList<>(2);
        a = ddc1155Service.balanceOfBatch(ddcs);
        System.out.println(a);
    }

    @Test
    void ddcURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.ddcURI(new BigInteger("3")));
    }

    @Test
    void setURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc1155Service.setURI(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", new BigInteger("3"), "1234"));
    }

    @Test
    void getNonce() throws Exception {
        BigInteger nonce = ddc1155Service.getNonce(DDCSdkClientTest.platform);
        System.out.println(nonce);
    }

    @Test
    void metaSafeMint() throws Exception {
        BigInteger amount = new BigInteger("3");
        byte[] data = {10, 10, 10};
        BigInteger nonce = ddc1155Service.getNonce(DDCSdkClientTest.platform).add(new BigInteger("1"));
        BigInteger deadline = new BigInteger("0");
        String ddcURI = "222222";
        String digest = metaTransaction.getSafeMintDigest(sender, amount, ddcURI, data, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);

        String hash = ddc1155Service.metaSafeMint(DDCSdkClientTest.platform,
                DDCSdkClientTest.platform,
                amount,
                ddcURI,
                data,
                nonce,
                deadline,
                signature
        );
        System.out.println(hash);
    }

    @Test
    void metaSafeTransferFrom() throws Exception {
        BigInteger amount = new BigInteger("3");
        byte[] data = {10, 10, 10};
        BigInteger nonce = ddc1155Service.getNonce(DDCSdkClientTest.platform).add(new BigInteger("1"));
        BigInteger ddcId = new BigInteger("1");
        BigInteger deadline = new BigInteger("1671505027000");
        String digest = metaTransaction.getSafeTransferFromDigest(DDCSdkClientTest.platform, DDCSdkClientTest.consumer, ddcId, amount, data, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        String hash = ddc1155Service.metaSafeTransferFrom(DDCSdkClientTest.platform,
                DDCSdkClientTest.platform,
                DDCSdkClientTest.consumer,
                ddcId,
                amount,
                data,
                nonce,
                deadline,
                signature
        );
        System.out.println(hash);
    }

    @Test
    void metaBurn() throws Exception {
        BigInteger ddcId = BigInteger.valueOf(11);
        BigInteger nonce = ddc1155Service.getNonce(DDCSdkClientTest.platform).add(new BigInteger("1"));
        BigInteger deadline = new BigInteger("1671505027000");
        String digest = metaTransaction.getBurnDigest(DDCSdkClientTest.platform, ddcId, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        String hash = ddc1155Service.metaBurn(DDCSdkClientTest.platform,
                DDCSdkClientTest.platform,
                ddcId,
                nonce,
                deadline,
                signature);
        System.out.println(hash);
    }
}