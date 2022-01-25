package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.listener.sign;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class DDC721ServiceTest {

    DDCSdkClient client = new DDCSdkClient.Builder()
            .setAuthorityLogicAddress("0xa7FC5B0F4A0085c5Ce689b919a866675Ce37B66b")
            .setChargeLogicAddress("0x3BBb01B38958d4dbF1e004611EbB3c65979B0511")
            .setDDC721Address("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e")
            .setDDC1155Address("0xe5d3b9E7D16E03A4A1060c72b5D1cb7806DD9070")
            .setGasLimit("300000")
            .setGasPrice("10000000")
            .setSignEventListener(new sign())
            .init();

    DDC721Service ddc721Service = client.getDDC721Service();

    String sender="";

    @Test
    void mint() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.mint(sender,"0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721","11111"));
    }

    @Test
    void approve() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.approve(sender,"6F561802FDAD741EDA7254C3F5651DAAAB266A90",new BigInteger("11")));
    }

    @Test
    void getApproved() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.getApproved(new BigInteger("2")));
    }

    @Test
    void setApprovalForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.setApprovalForAll(sender,"6F561802FDAD741EDA7254C3F5651DAAAB266A90",true));
    }

    @Test
    void isApprovedForAll() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.isApprovedForAll("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721","6F561802FDAD741EDA7254C3F5651DAAAB266A90"));
    }

    @Test
    void safeTransferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.safeTransferFrom(sender,"","",new BigInteger("2"),new byte[]{}));
    }

    @Test
    void transferFrom() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.transferFrom(sender,"","",new BigInteger("2")));
    }

    @Test
    void freeze() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.freeze(sender,new BigInteger("2")));
    }

    @Test
    void unFreeze() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.unFreeze(sender,new BigInteger("2")));
    }

    @Test
    void burn() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.burn(sender,new BigInteger("4")));
    }

    @Test
    void balanceOf() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.balanceOf("0x2A14331F1f2D3BA0D750f4c4916E69B1DC38d721"));
    }

    @Test
    void ownerOf() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.ownerOf(new BigInteger("2")));
    }

    @Test
    void name() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.name());
    }

    @Test
    void symbol() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.symbol());
    }

    @Test
    void ddcURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.ddcURI(new BigInteger("1")));
    }

    @Test
    void setURI() throws Exception {
        client.setGatewayUrl("https://opbtest.bsngate.com:18602/api/0e346e1fb134477cafb6c6c2583ce3c4/evmrpc");
        client.setGatewayApiKey("");
        System.out.println(ddc721Service.setURI(sender,new BigInteger(""),""));
    }
}