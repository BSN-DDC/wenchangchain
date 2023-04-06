package ai.bianjie.ddc.service;

import ai.bianjie.ddc.DDCSdkClient;
import ai.bianjie.ddc.DDCSdkClientTest;
import ai.bianjie.ddc.constant.CrossChainStateEnum;
import ai.bianjie.ddc.constant.DDCTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

class OPBCrossChainAppliedServiceTest {
    DDCSdkClient client = DDCSdkClientTest.getBSNClient();

    OPBCrossChainAppliedService opbCrossChainAppliedService = client.getOPBCrossChainAppliedService();
    String sender = DDCSdkClientTest.platform;

    @Test
    void crossChainTransfer() throws Exception {
        System.out.println(
                opbCrossChainAppliedService.crossChainTransfer(
                        // 签名账户地址
                        sender,
                        // DDC类型
                        DDCTypeEnum.ERC1155,
                        // DDC唯一标识
                        new BigInteger("80"),
                        // 是否锁定
                        Boolean.TRUE,
                        // 目标链chainID
                        new BigInteger("2"),
                        // 接收者账户地址
                        "0x36051ca0884645590d71196DBefe9FB32FEBdEf9",
                        // 附加数据
                        "0x".getBytes(StandardCharsets.UTF_8))
        );
    }

    @Test
    void updateCrossChainStatus() throws Exception {
        System.out.println(opbCrossChainAppliedService.updateCrossChainStatus(sender, new BigInteger("2"), CrossChainStateEnum.CROSS_CHAIN_SUCCESS, "no remark"));
    }
}