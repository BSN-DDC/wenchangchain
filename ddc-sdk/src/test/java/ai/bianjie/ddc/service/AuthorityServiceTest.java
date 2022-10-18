package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.SignEventTest;
import ai.bianjie.ddc.dto.AccountInfo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class AuthorityServiceTest {

    DDCSdkClient client = DDCSdkClientTest.getClient();

    AuthorityService authorityService = client.getAuthorityService();
    String sender = DDCSdkClientTest.platform;

    @Test
    void addAccountByPlatform() throws Exception {
        System.out.println(authorityService.addAccountByPlatform(sender, "0xd55172e02723cec9f0a89dbcdc1675098152ac52", "cs", "did:cs"));
    }

    @Test
    void addBatchAccountByPlatform() throws Exception {
        List<String> accountNames = new ArrayList<>();
        List<String> accountDIDs = new ArrayList<>();
        List<String> accountAddresses = new ArrayList<>();

        accountAddresses.add("0xA73786522046E2A8EB35D0292A9816087AD4043F");
        accountNames.add("cs3");
        accountDIDs.add("did:cs3");
        System.out.println(authorityService.addBatchAccountByPlatform(sender, accountNames, accountDIDs, accountAddresses));
    }

    @Test
    void testGetAccount() throws Exception {
        AccountInfo accountInfo = authorityService.getAccount(DDCSdkClientTest.consumer);
        System.out.println(accountInfo);
    }

    @Test
    void testUpdateAccState() throws Exception {
        System.out.println(authorityService.updateAccState(sender, "0x5804A5F927CE7382AD194FD25BCAA189DAD92A39", new BigInteger("0"), true));
    }

    @Test
    void switcherStateOfPlatform() throws Exception {
        System.out.println(authorityService.switcherStateOfPlatform());
    }
}