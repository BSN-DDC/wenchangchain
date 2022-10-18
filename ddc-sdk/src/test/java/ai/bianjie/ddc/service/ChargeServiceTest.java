package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.SignEventTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class ChargeServiceTest {
    DDCSdkClient client = DDCSdkClientTest.getClient();

    ChargeService chargeService = client.getChargeService();

    String sender = DDCSdkClientTest.platform;

    @Test
    void recharge() throws Exception {
        String hash = chargeService.recharge(DDCSdkClientTest.platform, DDCSdkClientTest.consumer, new BigInteger("10000"));
        System.out.print(hash);
        //0xaa99e1b786c77a0f71e03f5ce645cdd9c9c147ae67361cc45164a61bc7b5a053
    }

    @Test
    void balanceOf() throws Exception {
        BigInteger balance = chargeService.balanceOf(DDCSdkClientTest.consumer);
        System.out.print(balance);
    }

    @Test
    void queryFee() throws Exception {
        BigInteger fee = chargeService.queryFee("0x3B09b7A00271C5d9AE84593850dE3A526b8BF96e", "0xe985e9c5");
        System.out.print(fee);
    }

    @Test
    void rechargeBatch() throws Exception {
        List<String> addresses = new ArrayList<>();
        List<BigInteger> amounts = new ArrayList<>();
        addresses.add(DDCSdkClientTest.consumer);
        amounts.add(new BigInteger("10000"));
        System.out.println(chargeService.rechargeBatch(sender, addresses, amounts));

        //0x91bb20d66f2c45b51df9d9fdf801e9527ea640c67af61ba99f426e57b58b7175
    }

    @Test
    void balanceOfBatch() throws Exception {
        List<String> accAddrs = new ArrayList<>();
        accAddrs.add(DDCSdkClientTest.operator);
        accAddrs.add(DDCSdkClientTest.platform);
        accAddrs.add(DDCSdkClientTest.consumer);
        System.out.println(chargeService.balanceOfBatch(accAddrs));
    }//        accAddrs.add("0xa9f2e4519f429306900796cdc6a5d795635ddfe0");
}