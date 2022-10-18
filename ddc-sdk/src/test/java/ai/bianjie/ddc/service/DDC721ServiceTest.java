package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.util.HexUtils;
import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


class DDC721ServiceTest {
    DDCSdkClient client = DDCSdkClientTest.getClient();
    DDC721Service ddc721Service = client.getDDC721Service();
    String sender = "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E"; // platform
    String privateKey = "0xED43B9686AB520C896BC33A1461BAF163EDBF0DBC4D3199E77793A49B9BB2568";
    DDC721MetaTransaction metaTransaction = DDC721MetaTransaction.builder()
            .setChainId(BigInteger.valueOf(1223))
            .setContractAddress("0x63edD4A5C49B4caC9306F8FF01c32aDc023934cE")
            .build();

    @Test
    void mint() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.mint(sender, "0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E", "11111"));
    }

    @Test
    void safeMint() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        byte[] data = {10, 10, 10};
        System.out.println(ddc721Service.safeMint(sender, "0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "11111", data));
    }

    @Test
    void approve() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.approve(sender, "6F561802FDAD741EDA7254C3F5651DAAAB266A90", new BigInteger("11")));
    }

    @Test
    void getApproved() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.getApproved(new BigInteger("12")));
    }

    @Test
    void setApprovalForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.setApprovalForAll(sender, "6F561802FDAD741EDA7254C3F5651DAAAB266A90", true));
    }

    @Test
    void isApprovedForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.isApprovedForAll("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "6F561802FDAD741EDA7254C3F5651DAAAB266A90"));
    }

    @Test
    void safeTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.safeTransferFrom(sender, "0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("2"), new byte[]{10}));
    }

    @Test
    void transferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.transferFrom(sender, "0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721", "918F7F275A6C2D158E5B76F769D3F1678958A334", new BigInteger("2")));
    }

    @Test
    void burn() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.burn(sender, new BigInteger("4")));
    }

    @Test
    void balanceOf() throws Exception {
//        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
//        client.setGatewayApiKey("x-api-key");
//        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        client.setGatewayUrl("http://192.168.150.42:8545");
        System.out.println(ddc721Service.balanceOf("0x7FAF93F524FFDD1FB36BEC0ED6A167E8CE55BC4E"));
    }

    @Test
    void ownerOf() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.ownerOf(new BigInteger("12")));
    }

    @Test
    void name() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        System.out.println(ddc721Service.name());
    }

    @Test
    void symbol() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        System.out.println(ddc721Service.symbol());
    }

    @Test
    void ddcURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.ddcURI(new BigInteger("12")));
    }

    @Test
    void setURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/3c7c78de11494f219025f087bbacbd2a/evmrpc");
        client.setGatewayApiKey("x-api-key");
        client.setGatewayApiValue("5823d69e2198453e8662758e11cadacb");
        System.out.println(ddc721Service.setURI(sender, new BigInteger("12"), "1234"));
    }

    @Test
    void mintBatch() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        List<String> ddcURIs = new ArrayList<>();
        ddcURIs.add("123");
        ddcURIs.add("123");
        System.out.println(ddc721Service.mintBatch(sender, sender, ddcURIs));
    }

    @Test
    void safeMintBatch() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        List<String> ddcURIs = new ArrayList<>();
        ddcURIs.add("12345");
        byte[] data = {10, 12};
        System.out.println(ddc721Service.safeMintBatch(sender, sender, ddcURIs, data));
    }

    @Test
    void metaMint() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        String ddcURI = "http://ddcUrl";
        BigInteger nonce = client.getDDC721Service().getNonce(sender).add(new BigInteger("1"));
        BigInteger deadline = new BigInteger("0");
        String digest = metaTransaction.getMintDigest(sender, ddcURI, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        System.out.println(ddc721Service.metaMint(sender, sender, ddcURI, nonce, deadline, signature));
    }

    @Test
    void metaSafeMint() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        String ddcURI = "1";
        BigInteger nonce = client.getDDC721Service().getNonce(sender).add(BigInteger.valueOf(1));
        BigInteger deadline = new BigInteger("0");
        byte[] data = Numeric.hexStringToByteArray("hello，world！");
        String digest = metaTransaction.getSafeMintDigest(sender, ddcURI, data, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        System.out.println(ddc721Service.metaSafeMint(sender, sender, ddcURI, data, nonce, deadline, signature));
    }

    @Test
    void metaTransferFrom() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        BigInteger ddcId = BigInteger.valueOf(4);
        BigInteger nonce = client.getDDC721Service().getNonce(sender).add(BigInteger.valueOf(1));
        BigInteger deadline = new BigInteger("0");
        String digest = metaTransaction.getTransferFromDigest(sender, sender, ddcId, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        System.out.println(ddc721Service.metaTransferFrom(sender, sender, sender, ddcId, nonce, deadline, signature));
    }

    @Test
    void metaSafeTransferFrom() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        BigInteger ddcId = BigInteger.valueOf(5);
        BigInteger nonce = client.getDDC721Service().getNonce(sender).add(BigInteger.valueOf(1));
        BigInteger deadline = new BigInteger("0");
        byte[] data = Numeric.hexStringToByteArray("hello，world！");
        String digest = metaTransaction.getSafeTransferFromDigest(sender, sender, ddcId, data, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        System.out.println(ddc721Service.metaSafeTransferFrom(sender, sender, "0x32B6034EEBF55CEEF908816BE6709B234A2376D2", ddcId, data, nonce, deadline, signature));
    }

    @Test
    void metaBurn() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        BigInteger ddcId = BigInteger.valueOf(4);
        BigInteger nonce = client.getDDC721Service().getNonce(sender).add(BigInteger.valueOf(1));
        BigInteger deadline = new BigInteger("0");
        String digest = metaTransaction.getBurnDigest(ddcId, nonce, deadline);
        byte[] signature = metaTransaction.generateSignature(privateKey, digest);
        System.out.println(ddc721Service.metaBurn(sender, ddcId, nonce, deadline, signature));
    }

    @Test
    void getNonce() throws Exception {
        client.setGatewayUrl("http://192.168.150.42:8545");
        System.out.println(client.getDDC721Service().getNonce(sender));
    }
}