## BSN-DDC 文昌链跨链功能开发指引

> 本文介绍文昌链官方DDC 跨链功能详细步骤，跨链功能目前支持文昌链，武汉链，泰安链之间的互跨。如果您没有此需求可忽略此文。



### 参数信息

#### 链ID

  - 文昌链 chainId：`2`
  - 泰安链 chainId：`3`
  - 武汉链 chainId：`4`



#### 合约地址

- 文昌链权限代理合约地址：`0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29`
- 文昌链计费代理合约地址：`0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D`
- 文昌链DDC 721代理合约地址：`0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870`
- 文昌链DDC 1155代理合约地址：`0x0E762F4D11439B1130D402995328b634cB9c9973`
- 文昌链DDC OPB跨链应用代理合约地址：`0x0b563caa7F2Bd3E9b68C6e421973ddA2dD51f03a`



### 功能说明

#### 发起跨链交易


##### 1.初始化DDCSdkClient并调用跨链方法
​        为了从文昌链将DDC信息发送到其他开放联盟链，首先您需要初始化DDCSdkClient，调用 `crossChainTransfer` 方法发起跨链，参考示例代码：

``` java
// 注册签名事件
SignEventListener signEventListener = new sign();

// 签名处理示例
public String signEvent(RawTransaction rawTransaction) {
    Credentials credentials = Credentials.create("2F6976C530CFD2D0CC19EFC1868BD6A0AA1886D0BFCFA5A59D9B8899BE9B7241");
    byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
    return Numeric.toHexString(signMessage);
}

// 创建客户端
// 也可设置相关参数值 gasprice，gaslimit，相关合约地址
// irita 中 gaslimit 设置值即消耗值，扣费换算：1 uirita = 1e12 wei
// 建议 gaslimit 设置为3000000以上，gasprice 设置为10000000以上
DDCSdkClient client = new DDCSdkClient.Builder()
    // 权限代理合约地址
    .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29") 
    // 计费代理合约地址 
    .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D")    
    // DDC 721代理合约地址 
    .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870")   
    // DDC 1155代理合约地址
    .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973")    
    // OPB 跨链应用合约地址
    .setOpbCrossChainAddress("0x0b563caa7F2Bd3E9b68C6e421973ddA2dD51f03a")
    .setGasLimit("3000000")
    .setGasPrice("10000000")
    .setSignEventListener(new sign())
    .init();

// 设置网关
client.setGatewayUrl("https://opbningxia.bsngate.com:18602/api/[项目ID]/evmrpc");
// 设置key
client.setGatewayApiKey("[项目KEY]");
// 设置value
client.setGatewayValue("xxxxx");
// 设置连接超时时间（默认为10s）
client.setConnectTimeout(20);


// 可单独为每个方法设置gaslimit
BaseService baseService = new BaseService();
baseService.setFuncGasLimit("100000");

// 调用SDK方法发起跨链交易
String txhash = ddcSdkClient.getOPBCrossChainAppliedService().crossChainTransfer(
    // 文昌链签名账户地址
    "0x88d9a495d9c4b70a0d78b43a99b201bb314c8fd5", 
    // DDC类型
    DDCTypeEnum.ERC721,		
    // DDC唯一标识
    new BigInteger("1572"),	
    // 是否锁定
    Boolean.TRUE, 			
    // 目标链chianId
    new BigInteger("3"),    
    // 目标链接收者账户地址
    "0x36051ca0884645590d71196DBefe9FB32FEBdEf9",
    // 附加数据
    "0x".getBytes(StandardCharsets.UTF_8));
```

**请注意：**

- crossChainTransfer方法参数中的接收者账户地址一定要是目标链存在的账户，否则会交易失败。
- 类型为1155的ddc发起跨链时，需要该DDC的拥有者账户拥有全部数量时才可以发起跨链。



#### 确认跨链交易

​        发起跨链交易后，您需要通知目标链接收者账户去访问[BSN-DDC开放联盟链跨链网关OpenAPI](BSN-DDC/wuhanchain/blob/master/docs/BSN-DDC%E5%BC%80%E6%94%BE%E8%81%94%E7%9B%9F%E9%93%BE%E8%B7%A8%E9%93%BE%E7%BD%91%E5%85%B3OpenAPI.md) 去确认交易，才能在目标链执行跨链交易。因此目标链接收者账户需要进行以下步骤：

##### 1.获取跨链中心化服务访问权限

​        首先需要目标链链账户对自己的钱包地址进行签名，签名算法可参考[BSN-DDC开放联盟链跨链网关OpenAPI](BSN-DDC%E5%BC%80%E6%94%BE%E8%81%94%E7%9B%9F%E9%93%BE%E8%B7%A8%E9%93%BE%E7%BD%91%E5%85%B3OpenAPI.md) 的签名算法说明。签名后，请参见[BSN-DDC开放联盟链跨链网关OpenAPI](BSN-DDC%E5%BC%80%E6%94%BE%E8%81%94%E7%9B%9F%E9%93%BE%E8%B7%A8%E9%93%BE%E7%BD%91%E5%85%B3OpenAPI.md)的**【获取Token】**接口，得到访问Token。

  ##### 2.查询需要签名确认的跨链交易

​        获取到Token后，继续参见[BSN-DDC开放联盟链跨链网关OpenAPI](BSN-DDC%E5%BC%80%E6%94%BE%E8%81%94%E7%9B%9F%E9%93%BE%E8%B7%A8%E9%93%BE%E7%BD%91%E5%85%B3OpenAPI.md)的【**查询需要签名确认的跨链交易**】接口，得到需要签名确认的跨链交易的起始链交易hash，以便确认跨链交易使用。

  ##### 3.确认跨链交易

​        跨链交易只有目标链账户确认后才能继续进行，为了证明是目标链接收者账户亲自确认交易，目标链账户需要使用第1步获取到的签名和第2步获取到的起始链交易hash，结合目标链接收者账户地址，参见 [BSN-DDC开放联盟链跨链网关OpenAPI](BSN-DDC%E5%BC%80%E6%94%BE%E8%81%94%E7%9B%9F%E9%93%BE%E8%B7%A8%E9%93%BE%E7%BD%91%E5%85%B3OpenAPI.md)的【**确认跨链交易**】接口，进行确认。跨链交易确认完成后，BSN-DDC跨链网关会自动向目标链发起跨链交易。
