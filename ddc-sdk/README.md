# DDC-SDK-JAVA

- [DDC-SDK-JAVA](#ddc-sdk-java)
  - [平台方可调用的如下方法：](#平台方可调用的如下方法)
    - [1.初始化Client (连接测试网)](#1初始化client-连接测试网)
    - [2.BSN-DDC-权限管理](#2bsn-ddc-权限管理)
    - [3.BSN-DDC-费用管理](#3bsn-ddc-费用管理)
    - [4.BSN-DDC-721](#4bsn-ddc-721)
    - [5.BSN-DDC-1155](#5bsn-ddc-1155)
    - [6.BSN-DDC-交易查询](#6bsn-ddc-交易查询)
    - [7.BSN-DDC-区块查询](#7bsn-ddc-区块查询)
    - [8.BSN-DDC-数据解析](#8bsn-ddc-数据解析)
    - [9.离线账户创建](#9离线账户创建)
    - [10.查询Gas余额](#10查询gas余额)
    - [11.设置账户 nonce](#11设置账户-nonce)
  - [终端用户可调用的如下方法：](#终端用户可调用的如下方法)
    - [1.初始化Client (连接测试网)](#1初始化client-连接测试网-1)
    - [2.BSN-DDC-权限管理](#2bsn-ddc-权限管理-1)
    - [3.BSN-DDC-费用管理](#3bsn-ddc-费用管理-1)
    - [4.BSN-DDC-721](#4bsn-ddc-721-1)
    - [5.BSN-DDC-1155](#5bsn-ddc-1155-1)
    - [6.BSN-DDC-数据解析](#6bsn-ddc-数据解析)
    - [9.离线账户创建](#9离线账户创建-1)
    - [10.查询Gas余额](#10查询gas余额-1)
    - [11.设置账户 nonce](#11设置账户-nonce-1)
  - [测试用例](#测试用例)

## 平台方可调用的如下方法：

### 1.初始化Client (连接测试网)

```
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
    // 建议 gaslimit 设置为300000以上，gasprice 设置为10000000以上
     DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xdAc50c90b934AdED33b6ADc9f5855ab8a9EFB09a")
                .setChargeLogicAddress("0x52403cE9E235Cf013bA2353F0bf47834C98424c7")
                .setDDC721Address("0x503f45958F57Da55170B54796F4eD224c9fef9d7")
                .setDDC1155Address("0xe7310D2D79c67a3078DBeFA67344c7047AC28708")
                .setGasLimit("300000")
                .setGasPrice("10000000")
                .setSignEventListener(new sign())
                .init();

     // 设置网关
     client.setGatewayUrl("192.168.42.1");
     // 设置key
     client.setGatewayApiKey("x-api-key");
     // 设置value
     client.setGatewayValue("xxxxx");
     // 设置连接超时时间（默认为10s）
     client.setConnectTimeout(20);
    

    // 可单独为每个方法设置gaslimit
    BaseService baseService = new BaseService();
    baseService.setFuncGasLimit("100000");
    
    // 进行交易的方法需要传入sender参数，即方法调用者地址
```

### 2.BSN-DDC-权限管理

```
     AuthorityService authorityService = client.getAuthorityService(); 
     
    // 添加账户
    // sender   调用者地址
    // account   DDC链账户地址
    // accName   DDC账户对应的账户名称
    // accDID    DDC账户对应的DID信息
    // 返回交易哈希
    String Txhash2 = authorityService.addAccountByPlatform(sender，account, accName, accDID);
    
    // 批量添加账户
    List<String> accountNames = new ArrayList<>();
        List<String> accountDIDs = new ArrayList<>();
        List<String> accountAddresses = new ArrayList<>();
        accountAddresses.add(DDCSdkClientTest.consumer);
        accountNames.add("cs3");
        accountDIDs.add("did:cs3");
        System.out.println(authorityService.addBatchAccountByPlatform(sender, accountNames,accountDIDs,accountAddresses));
  
    // 查询账户
    
	// account DDC用户链账户地址
    // 返回DDC账户信息
    AccountInfo info = authorityService.getAccount(account);
    
    // 更新账户状态
    
    // account DDC用户链账户地址
    // state   枚举，状态 ：Frozen - 冻结状态 ； Active - 活跃状态
    // changePlatformState
    // 返回交易哈希
    String Txhash3 = updateAccState(account, 1， false)
    
    // 查询平台方添加链账户开关状态
    System.out.println(authorityService.switcherStateOfPlatform());
```

### 3.BSN-DDC-费用管理

```
    ChargeService chargeService = client.getChargeService();  
    
    // 充值
    
    // to 充值账户的地址
	// amount 充值金额
	// 返回交易哈希
    String Txhash1 = chargeService.recharge(to, BigInteger.valueOf(10000));  
    
    // 批量充值
     List<String> addresses = new ArrayList<>();
     List<BigInteger> amounts = new ArrayList<>();
     addresses.add(DDCSdkClientTest.consumer);
     amounts.add(new BigInteger("10000"));
     System.out.println(chargeService.rechargeBatch(sender, addresses, amounts));
    
    // 链账户余额查询
    // accAddr 查询的账户地址
	// 返回账户所对应的业务费余额
    BigInteger balance = chargeService.balanceOf(accAddr);
    
    // 批量链账户余额查询
    List<String> accAddrs = new ArrayList<>();
        accAddrs.add("0x02CEB40D892061D457E7FA346988D0FF329935DF");
        System.out.println(chargeService.balanceOfBatch(accAddrs));
    
    // DDC计费规则查询
    
    // ddcAddr DDC业务主逻辑合约地址
	// sig Hex格式的合约方法ID
	// 返回DDC合约业务费
    BigInteger fee = queryFee(ddcAddr, "0x36351c7c");
```

### 4.BSN-DDC-721

```
    DDC721Service ddc721Service = client.getDDC721Service(); 
    
    // 生成
    
    // to     接收者账户
    // ddcURI DDC资源标识符
    // 返回交易哈希
    String Txhash = ddc721Service.mint(toaddr, ddcURI);
    
    // 安全生成
    
    // sender 调用者地址
    // to     接收者账户
    // ddcURI DDC资源标识符
    ddc721Service.safeMint(sender, to, ddcURI,data);
    
    // DDC授权
    
    // to    授权者账户
    // ddcId DDC唯一标识
    // 返回交易哈希
    String Txhash1 = ddc721Service.approve(to, ddcId);
    
    // DDC授权查询
    
    // ddcId DDC唯一标识
    // 返回授权的账户
    String account = ddc721Service.getApproved(ddcId);
    
    // 账户授权
    
    // operator 授权者账户
    // approved 授权标识
    // 返回交易hash
    String Txhash2 = ddc721Service.setApprovalForAll(operator, true);
    
    // 账户授权查询
    
    // owner    拥有者账户
    // operator 授权者账户
    // 返回授权标识
    Boolean result = ddc721Service.isApprovedForAll(owner, operator);
    
    // 安全转移
    
    // from  拥有者账户
    // to    授权者账户
    // ddcId DDC唯一标识
    // data  附加数据
    // 返回交易hash
    String Txhash3 = ddc721Service.safeTransferFrom(from, to, ddcId, data);
    
    // 转移
    
    // from  拥有者账户
    // to    接收者账户
    // ddcId ddc唯一标识
    // 返回交易hash
    String Txhash4 = ddc721Service.transferFrom(from, to, ddcId);
    
    // 销毁
    
    // ddcId DDC唯一标识
    // 返回交易hash
    String Txhash6 = ddc721Service.burn(ddcId);
    
    // 查询数量
    
    // owner 拥有者账户
    // 返回ddc的数量
    BigInteger num = ddc721Service.balanceOf(owner);
    
    // 查询拥有者
    
    // ddcId ddc唯一标识
    // 返回拥有者账户
    String account = ddc721Service.ownerOf(ddcId);
    
    // 获取名称
    
    // 返回DDC运营方名称
    String name = ddc721Service.name();
    
    // 获取符号
    
    // 返回DDC运营方符号
    String symbol = ddc721Service.symbol();
    
    // 获取DDCURI
    
    // 返回DDC资源标识符
    String ddcURI = ddc721Service.ddcURI(ddcId);
    
    // 设置ddcURL
    // ddc拥有者或授权者，ddcid，ddcURL
    ddc721Service.setURI(sender,new BigInteger(""),"")
    
    // 批量生成
    List<String> ddcURIs = new ArrayList<>();
    ddcURIs.add("123");
    ddcURIs.add("123");
    System.out.println(ddc721Service.mintBatch(sender, sender, DDC资源标识符列表ddcURIs))
    
    // 批量安全生成
    List<String> ddcURIs = new ArrayList<>();
    ddcURIs.add("12345");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");
    System.out.println(ddc721Service.safeMintBatch(sender, sender, DDC资源标识符列表ddcURIs, 附加数据data));
    
    // 元交易生成
    String ddcURI = "1";
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x08de2782f7e83b9ef8b07b77e52984f13aff9dfc8c49b44d4963b786a59e31361656e29d528f10e4b49395066439a2413ae231426d38dba9c71ab419d54a52731c");
    String hash = ddc721Service.metaMint("交易发起者链账户地址", "DDC接收者链账户地址", "DDC资源标识符ddcURI", "通过合约维护的nonce", 时间戳deadline, 签名值sign)
    
    // 元交易安全生成
    String ddcURI = "1";
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");  
    byte[] sign = Numeric.hexStringToByteArray("0x08de2782f7e83b9ef8b07b77e52984f13aff9dfc8c49b44d4963b786a59e31361656e29d528f10e4b49395066439a2413ae231426d38dba9c71ab419d54a52731c");
    String hash = ddc721Service.metaSafeMint("交易发起者链账户地址", "DDC接收者链账户地址", "DDC资源标识符ddcURI","附加数据data", "通过合约维护的nonce", 时间戳deadline, 签名值sign)
    
    // 元交易转移
    BigInteger ddcId = BigInteger.valueOf(4);
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x7c42708260845975c47ceeaed5d242d0ac039ca032c2edf0aae93a936720cdb21cc5eec5fcf3a658ae098116d563cbccf2a37f1dc2449f552b6d67cdd7c8318e1b");
    System.out.println(ddc721Service.metaTransferFrom("交易发起者链账户地址", "DDC拥有者或授权者链账户地址", "DDC接收者链账户地址", ddcId, "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // 元交易安全转移
    BigInteger ddcId = BigInteger.valueOf(5);
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");
    byte[] sign = Numeric.hexStringToByteArray("0x49de1cd0c0a787e32951ea832fbf2ccbd3d8fdce58739afad2bd66846ffc31247172b1cea80e2d642a0fde707601dcb2f888057475fc2f984314ee5162f82e301b");
    System.out.println(ddc721Service.metaSafeTransferFrom("交易发起者链账户地址", "DDC拥有者或授权者链账户地址", "DDC接收者链账户地址", ddcId, "附加数据data", "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // 元交易销毁
    BigInteger ddcId = BigInteger.valueOf(4);
    BigInteger nonce = BigInteger.valueOf(3);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x05001f4f976c7c96aa829192e4b5d40cd637e373442a44d6768fe595553f737e2f53ede1816a3b2a9b4df26864896aa97400d29c92643c3859862cf8fe83f3a21b");
    System.out.println(ddc721Service.metaBurn("DDC拥有者或授权者链账户地址", ddcId, "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // Nonce查询
    BigInteger nonce = client.getDDC721Service().getNonce("链账户地址")
```

### 5.BSN-DDC-1155

```
    DDC1155Service ddc1155Service = client.getDDC1155Service(); 
    
    // 生成
    String Txhash  = ddc1155Service.mint(to, amount, ddcURI);
    
    // 批量生成
    String Txhash  = ddc1155Service.mintBatch(to, ddcInfo);
    
    // 账户授权
    
    // operator 授权者账户
    // approved 授权标识
    // 返回交易哈希
    String Txhash1  = ddc1155Service.setApprovalForAll(operator, approved);
    
    // 账户授权查询
    
    // owner    拥有者账户
    // operator 授权者账户
    // 返回授权结果（boolean）
    Boolean result = isApprovedForAll(owner, operator);
    
    // 安全转移
    
    // from   拥有者账户
    // to     接收者账户
    // ddcId  DDCID
    // amount 需要转移的DDC数量
    // data   附加数据
    // 返回交易哈希
    String Txhash2  = ddc1155Service.safeTransferFrom(from, to, ddcId, amount, data);
    
    // 批量安全转移
    
    // from 拥有者账户
    // to   接收者账户
    // ddcs 拥有者DDCID集合
    // data 附加数据
    // 返回交易哈希
    String Txhash3  = ddc1155Service.safeBatchTransferFrom(from, to, ddcs, data);
   
    // 销毁
    String Txhash6  = ddc1155Service.burn(owner, ddcId);
    
    // 批量销毁
    String Txhash7  = ddc1155Service.burnBatch(owner, ddcIds);
    
    // 查询数量
    BigInteger num = balanceOf(owner, ddcId);
    
    // 批量查询数量
    List<BigInteger> num= balanceOfBatch(ddcs);
    
    // 获取DDCURI
    String ddcURI = ddcURI(ddcId);
    
    // 设置ddcURL
    // 调用者，ddc拥有者或授权者，ddcid，ddcURL
    ddc721Service.setURI(sender,owner，new BigInteger(""),"")
    
    // Nonce查询
    BigInteger Nonce = ddc1155Service.getNonce(owner)
    
    // 元交易安全生成
    String Txhash8 = ddc1155Service.metaSafeMint( sender,  to,  amount,  ddcURI,  data,  nonce,  deadline,  sign)
    
    // 元交易安全转移
    String Txhash9 = ddc1155Service.metaSafeTransferFrom( sender, from, to, ddcId, amount, data, nonce, deadline, sign)
    
    // 元交易安全销毁
    String Txhash10 = ddc1155Service.metaBurn( sender, owner, ddcId, amount, data, nonce, deadline, sign)
    
```

### 6.BSN-DDC-交易查询

```
	BaseService baseService = new BaseService();
	
	// 查询交易回执
	// hash 交易哈希
    // 返回交易回执
     TransactionReceipt TxReceipt = baseService.getTransReceipt(hash)
     
     // 查询交易信息
     // hash 交易哈希
     // 返回交易信息
     Transaction Tx = baseService.getTransByHash(hash)
     
     // 查询交易状态
     // hash 交易哈希
     // 返回交易状态
     Boolean state = baseService.getTransByStatus(hash)

```

### 7.BSN-DDC-区块查询

```
	BaseService baseService = new BaseService();
	
    // 获取区块信息
    EthBlock.Block blockinfo = baseService.getBlockByNumber(blockNumber)
    
```

### 8.BSN-DDC-数据解析

```
7.BSN-DDC-数据解析
    7.1权限数据
        7.1.1添加账户
        7.1.2更新账户状态
        7.1.3添加账户开关设置
        7.1.4批量添加账户
        7.1.5同步平台方DID

    7.2充值数据
        7.2.1充值
        7.2.2DDC业务费扣除
        7.2.3设置DDC计费规则
        7.2.4删除DDC计费规则
        7.2.5按合约删除DDC计费规则
        7.2.6批量充值

    7.3BSN-DDC-721数据
        7.3.1生成
        7.3.2转移/安全转移
        7.3.3冻结
        7.3.4解冻
        7.3.5销毁
        7.3.6批量生成/批量安全生成
        7.3.7元交易生成
        7.3.8元交易安全生成
        7.3.9元交易转移
        7.3.10元交易安全转移
        7.3.11元交易销毁

    7.4BSN-DDC-1155数据
        7.4.1生成
        7.4.2批量生成
        7.4.3安全转移
        7.4.4批量安全转移
        7.4.5冻结
        7.4.6解冻
        7.4.7销毁
        7.4.8批量销毁
        7.4.9元交易安全生成
        7.4.10元交易安全转移
        7.4.11元交易销毁
```

```
	BlockEventService blockEventService = new BlockEventService();
	// 获取区块事件并解析
	// 1. 根据块高获取区块信息
    // 2. 根据块中交易获取交易回执
    // 3. 遍历交易回执中的事件并解析
    // blockNumber 块高
    // 返回 ArrayList<Object>

	ArrayList<BaseEventResponse> blockEvent = blockEventService.getBlockEvent("28684");
	blockEvent.forEach(b->{
            System.out.println(b.log);
        });
        
```

### 9.离线账户创建

```
// 创建Hex格式账户
// 返回包含助记词，公钥，私钥，hex格式地址的Account对象
BaseService baseService=new BaseService();
        Account acc = baseService.createAccount();
        System.out.println("================================" + acc.getAddress());
        
// Hex格式账户转换为Bech32格式账户
        String addHex= baseService.AccountHexToBech32(acc.getAddress());
        System.out.println("================================" + addHex);
```

### 10.查询Gas余额

```
// 查询链账户Gas余额
System.out.println(baseService.BalanceOfGas("链账户地址"));
```

### 11.设置账户 nonce

```
BaseService baseService =new BaseService();
baseService.setNonce(new BigInteger("481"));
```

```
    void nonceTest() throws Exception {

	// 初始化 DDC 客户端      
        DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29") // 官方合约地址
                .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D") // 官方合约地址
                .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870") // 官方合约地址
                .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973") // 官方合约地址
                .setGasLimit("300000") // 自定义 Gas 上限
                .setGasPrice("1") // 固定 Gas Price，无需修改
                .setSignEventListener(new SignEventTest()) // 签名事件示例，建议参考示例自行实现
                .init();

        client.setGatewayUrl("https:// opbningxia.bsngate.com:18602/api/项目ID/evmrpc"); // EVM RPC 地址
        client.setConnectTimeout(20);// 请求超时时间，自定义
        String sender = "平台方链账户地址"; // 平台方链账户地址

      	// 以充值接口为例
        ChargeService chargeService = client.getChargeService();

        // 链上查询最新的 Nonce
        EthGetTransactionCount ethGetTransactionCount = Web3jUtils.getWeb3j().ethGetTransactionCount(sender, DefaultBlockParameterName.PENDING).sendAsync().get();
        BigInteger txNonce = ethGetTransactionCount.getTransactionCount();

      	// 循环调用，每调用一次 Nonce 离线加一，不需要重新从链上查询
        for (int i = 1; i < 10; i++) {
            // 设置 Nonce
            chargeService.setNonce(txNonce); 
            // 发送请求
            String hash = chargeService.recharge(sender, "被充值的链账户地址", new BigInteger("充值业务费数量"));
            // Nonce 离线加一
            txNonce = txNonce.add(new BigInteger("1"));
        }
    }
```

## 终端用户可调用的如下方法：

### 1.初始化Client (连接测试网)

```
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
    // 建议 gaslimit 设置为300000以上，gasprice 设置为10000000以上
     DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xdAc50c90b934AdED33b6ADc9f5855ab8a9EFB09a")
                .setChargeLogicAddress("0x52403cE9E235Cf013bA2353F0bf47834C98424c7")
                .setDDC721Address("0x503f45958F57Da55170B54796F4eD224c9fef9d7")
                .setDDC1155Address("0xe7310D2D79c67a3078DBeFA67344c7047AC28708")
                .setGasLimit("300000")
                .setGasPrice("10000000")
                .setSignEventListener(new sign())
                .init();

     // 设置网关
     client.setGatewayUrl("192.168.42.1");
     // 设置key
     client.setGatewayApiKey("x-api-key");
     // 设置value
     client.setGatewayValue("xxxxx");
     // 设置连接超时时间（默认为10s）
     client.setConnectTimeout(20);
    

    // 可单独为每个方法设置gaslimit
    BaseService baseService = new BaseService();
    baseService.setFuncGasLimit("100000");
    
    // 进行交易的方法需要传入sender参数，即方法调用者地址
```

### 2.BSN-DDC-权限管理

```
	AuthorityService authorityService = client.getAuthorityService(); 
    
    // 查询账户
    
	// account DDC用户链账户地址
    // 返回DDC账户信息
    AccountInfo info = authorityService.getAccount(account);
```

### 3.BSN-DDC-费用管理

```
    ChargeService chargeService = client.getChargeService();  
    
    // 链账户余额查询
    
    // accAddr 查询的账户地址
	// 返回账户所对应的业务费余额
    BigInteger balance = chargeService.balanceOf(accAddr);
    
    // 批量链账户余额查询
    List<String> accAddrs = new ArrayList<>();
        accAddrs.add("查询的账户地址");
        System.out.println(chargeService.balanceOfBatch(accAddrs));
    
    // DDC计费规则查询
    
    // ddcAddr DDC业务主逻辑合约地址
	// sig Hex格式的合约方法ID
	// 返回DDC合约业务费
    BigInteger fee = queryFee(ddcAddr, "0x36351c7c");
    
```

### 4.BSN-DDC-721

```
     DDC721Service ddc721Service = client.getDDC721Service(); 
    
    // 生成
    
    // to     接收者账户
    // ddcURI DDC资源标识符
    // 返回交易哈希
    String Txhash = ddc721Service.mint(toaddr, ddcURI);
    
    // 安全生成

    // sender 调用者地址
    // to     接收者账户
    // ddcURI DDC资源标识符
    ddc721Service.safeMint(sender, to, ddcURI,data);
    
    // DDC授权
    
    // to    授权者账户
    // ddcId DDC唯一标识
    // 返回交易哈希
    String Txhash1 = ddc721Service.approve(to, ddcId);
    
    // DDC授权查询
    
    // ddcId DDC唯一标识
    // 返回授权的账户
    String account = ddc721Service.getApproved(ddcId);
    
    // 账户授权
    
    // operator 授权者账户
    // approved 授权标识
    // 返回交易hash
    String Txhash2 = ddc721Service.setApprovalForAll(operator, true);
    
    // 账户授权查询
    
    // owner    拥有者账户
    // operator 授权者账户
    // 返回授权标识
    Boolean result = ddc721Service.isApprovedForAll(owner, operator);
    
    // 安全转移
    
    // from  拥有者账户
    // to    授权者账户
    // ddcId DDC唯一标识
    // data  附加数据
    // 返回交易hash
    String Txhash3 = ddc721Service.safeTransferFrom(from, to, ddcId, data);
    
    // 转移
    
    // from  拥有者账户
    // to    接收者账户
    // ddcId ddc唯一标识
    // 返回交易hash
    String Txhash4 = ddc721Service.transferFrom(from, to, ddcId);
    
    // 销毁
    
    // ddcId DDC唯一标识
    // 返回交易hash
    String Txhash6 = ddc721Service.burn(ddcId);
    
    // 查询数量
    
    // owner 拥有者账户
    // 返回ddc的数量
    BigInteger num = ddc721Service.balanceOf(owner);
    
    // 查询拥有者
    
    // ddcId ddc唯一标识
    // 返回拥有者账户
    String account = ddc721Service.ownerOf(ddcId);
    
    // 获取名称
    
    // 返回DDC运营方名称
    String name = ddc721Service.name();
    
    // 获取符号
    
    // 返回DDC运营方符号
    String symbol = ddc721Service.symbol();
    
    // 获取DDCURI
    
    // 返回DDC资源标识符
    String ddcURI = ddc721Service.ddcURI(ddcId);
    
    // 设置ddcURL
    // ddc拥有者或授权者，ddcid，ddcURL
    ddc721Service.setURI(sender,new BigInteger(""),"")
    
    // 批量生成
    List<String> ddcURIs = new ArrayList<>();
    ddcURIs.add("123");
    ddcURIs.add("123");
    System.out.println(ddc721Service.mintBatch(sender, sender, DDC资源标识符列表ddcURIs))
    
    // 批量安全生成
    List<String> ddcURIs = new ArrayList<>();
    ddcURIs.add("12345");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");
    System.out.println(ddc721Service.safeMintBatch(sender, sender, DDC资源标识符列表ddcURIs, 附加数据data));
    
    // 元交易生成
    String ddcURI = "1";
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x08de2782f7e83b9ef8b07b77e52984f13aff9dfc8c49b44d4963b786a59e31361656e29d528f10e4b49395066439a2413ae231426d38dba9c71ab419d54a52731c");
    String hash = ddc721Service.metaMint("交易发起者链账户地址", "DDC接收者链账户地址", "DDC资源标识符ddcURI", "通过合约维护的nonce", 时间戳deadline, 签名值sign)
    
    // 元交易安全生成
    String ddcURI = "1";
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");  
    byte[] sign = Numeric.hexStringToByteArray("0x08de2782f7e83b9ef8b07b77e52984f13aff9dfc8c49b44d4963b786a59e31361656e29d528f10e4b49395066439a2413ae231426d38dba9c71ab419d54a52731c");
    String hash = ddc721Service.metaSafeMint("交易发起者链账户地址", "DDC接收者链账户地址", "DDC资源标识符ddcURI","附加数据data", "通过合约维护的nonce", 时间戳deadline, 签名值sign)
    
    // 元交易转移
    BigInteger ddcId = BigInteger.valueOf(4);
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x7c42708260845975c47ceeaed5d242d0ac039ca032c2edf0aae93a936720cdb21cc5eec5fcf3a658ae098116d563cbccf2a37f1dc2449f552b6d67cdd7c8318e1b");
    System.out.println(ddc721Service.metaTransferFrom("交易发起者链账户地址", "DDC拥有者或授权者链账户地址", "DDC接收者链账户地址", ddcId, "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // 元交易安全转移
    BigInteger ddcId = BigInteger.valueOf(5);
    BigInteger nonce = BigInteger.valueOf(2);
    BigInteger deadline = new BigInteger("0");
    byte[] data = Numeric.hexStringToByteArray("hello，world！");
    byte[] sign = Numeric.hexStringToByteArray("0x49de1cd0c0a787e32951ea832fbf2ccbd3d8fdce58739afad2bd66846ffc31247172b1cea80e2d642a0fde707601dcb2f888057475fc2f984314ee5162f82e301b");
    System.out.println(ddc721Service.metaSafeTransferFrom("交易发起者链账户地址", "DDC拥有者或授权者链账户地址", "DDC接收者链账户地址", ddcId, "附加数据data", "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // 元交易销毁
    BigInteger ddcId = BigInteger.valueOf(4);
    BigInteger nonce = BigInteger.valueOf(3);
    BigInteger deadline = new BigInteger("0");
    byte[] sign = Numeric.hexStringToByteArray("0x05001f4f976c7c96aa829192e4b5d40cd637e373442a44d6768fe595553f737e2f53ede1816a3b2a9b4df26864896aa97400d29c92643c3859862cf8fe83f3a21b");
    System.out.println(ddc721Service.metaBurn("DDC拥有者或授权者链账户地址", ddcId, "通过合约维护的nonce", 时间戳deadline, 签名值sign));
    
    // Nonce查询
    BigInteger nonce = client.getDDC721Service().getNonce("链账户地址")
```

### 5.BSN-DDC-1155

```
    DDC1155Service ddc1155Service = client.getDDC1155Service(); 
    
    // 生成
    String Txhash  = ddc1155Service.mint(to, amount, ddcURI);
    
    // 批量生成
    String Txhash  = ddc1155Service.mintBatch(to, ddcInfo);
    
    // 账户授权
    
    // operator 授权者账户
    // approved 授权标识
    // 返回交易哈希
    String Txhash1  = ddc1155Service.setApprovalForAll(operator, approved);
    
    // 账户授权查询
    
    // owner    拥有者账户
    // operator 授权者账户
    // 返回授权结果（boolean）
    Boolean result = isApprovedForAll(owner, operator);
    
    // 安全转移
    
    // from   拥有者账户
    // to     接收者账户
    // ddcId  DDCID
    // amount 需要转移的DDC数量
    // data   附加数据
    // 返回交易哈希
    String Txhash2  = ddc1155Service.safeTransferFrom(from, to, ddcId, amount, data);
    
    // 批量安全转移
    
    // from 拥有者账户
    // to   接收者账户
    // ddcs 拥有者DDCID集合
    // data 附加数据
    // 返回交易哈希
    String Txhash3  = ddc1155Service.safeBatchTransferFrom(from, to, ddcs, data);
   
    // 销毁
    String Txhash6  = ddc1155Service.burn(owner, ddcId);
    
    // 批量销毁
    String Txhash7  = ddc1155Service.burnBatch(owner, ddcIds);
    
    // 查询数量
    BigInteger num = balanceOf(owner, ddcId);
    
    // 批量查询数量
    List<BigInteger> num= balanceOfBatch(ddcs);
    
    // 获取DDCURI
    String ddcURI = ddcURI(ddcId);
    
    // 设置ddcURL
    // 调用者，ddc拥有者或授权者，ddcid，ddcURL
    ddc721Service.setURI(sender,owner，new BigInteger(""),"")
    
    // Nonce查询
    BigInteger Nonce = ddc1155Service.getNonce(owner)
    
    // 元交易安全生成
    String Txhash8 = ddc1155Service.metaSafeMint( sender,  to,  amount,  ddcURI,  data,  nonce,  deadline,  sign)
    
    // 元交易安全转移
    String Txhash9 = ddc1155Service.metaSafeTransferFrom( sender, from, to, ddcId, amount, data, nonce, deadline, sign)
    
    // 元交易安全销毁
    String Txhash10 = ddc1155Service.metaBurn( sender, owner, ddcId, amount, data, nonce, deadline, sign)
```

### 6.BSN-DDC-数据解析

```
5.BSN-DDC-数据解析
    5.1权限数据
        5.1.1添加账户
        5.1.2更新账户状态

    5.2充值数据
        5.2.1充值
        5.2.2DDC业务费扣除
        5.2.3设置DDC计费规则
        5.2.4删除DDC计费规则
        5.2.5按合约删除DDC计费规则

    5.3BSN-DDC-721数据
        5.3.1生成
        5.3.2转移/安全转移
        5.3.3冻结
        5.3.4解冻
        5.3.5销毁

    5.4BSN-DDC-1155数据
        5.4.1生成
        5.4.2批量生成
        5.4.3安全转移
        5.4.4批量安全转移
        5.4.5冻结
        5.4.6解冻
        5.4.7销毁
        5.4.8批量销毁
```

```
	BlockEventService blockEventService = new BlockEventService();
	// 获取区块事件并解析
	// 1. 根据块高获取区块信息
    // 2. 根据块中交易获取交易回执
    // 3. 遍历交易回执中的事件并解析
    // blockNumber 块高
    // 返回 ArrayList<Object>

	ArrayList<BaseEventResponse> blockEvent = blockEventService.getBlockEvent("28684");
	blockEvent.forEach(b->{
            System.out.println(b.log);
        });
        
```

### 9.离线账户创建

```
// 创建Hex格式账户
// 返回包含助记词，公钥，私钥，hex格式地址的Account对象
BaseService baseService=new BaseService();
        Account acc = baseService.createAccount();
        System.out.println("================================" + acc.getAddress());
        
// Hex格式账户转换为Bech32格式账户
        String addHex= baseService.AccountHexToBech32(acc.getAddress());
        System.out.println("================================" + addHex);
```

### 10.查询Gas余额

```
// 查询链账户Gas余额
System.out.println(baseService.BalanceOfGas("链账户地址"));
```

### 11.设置账户 nonce

```
BaseService baseService =new BaseService();
baseService.setNonce(new BigInteger("481"));
```

    void nonceTest() throws Exception {
    
    // 初始化 DDC 客户端      
        DDCSdkClient client = new DDCSdkClient.Builder()
                .setAuthorityLogicAddress("0xFa1d2d3EEd20C4E4F5b927D9730d9F4D56314B29") // 官方合约地址
                .setChargeLogicAddress("0x0B8ae0e1b4a4Eb0a0740A250220eE3642d92dc4D") // 官方合约地址
                .setDDC721Address("0x354c6aF2cB870BEFEA8Ea0284C76e4A46B8F2870") // 官方合约地址
                .setDDC1155Address("0x0E762F4D11439B1130D402995328b634cB9c9973") // 官方合约地址
                .setGasLimit("300000") // 自定义 Gas 上限
                .setGasPrice("1") // 固定 Gas Price，无需修改
                .setSignEventListener(new SignEventTest()) // 签名事件示例，建议参考示例自行实现
                .init();
    
        client.setGatewayUrl("https:// opbningxia.bsngate.com:18602/api/项目ID/evmrpc"); // EVM RPC 地址
        client.setConnectTimeout(20);// 请求超时时间，自定义
        String sender = "平台方链账户地址"; // 平台方链账户地址
    
      	// 以充值接口为例
        ChargeService chargeService = client.getChargeService();
    
        // 链上查询最新的 Nonce
        EthGetTransactionCount ethGetTransactionCount = Web3jUtils.getWeb3j().ethGetTransactionCount(sender, DefaultBlockParameterName.PENDING).sendAsync().get();
        BigInteger txNonce = ethGetTransactionCount.getTransactionCount();
    
      	// 循环调用，每调用一次 Nonce 离线加一，不需要重新从链上查询
        for (int i = 1; i < 10; i++) {
            // 设置 Nonce
            chargeService.setNonce(txNonce); 
            // 发送请求
            String hash = chargeService.recharge(sender, "被充值的链账户地址", new BigInteger("充值业务费数量"));
            // Nonce 离线加一
            txNonce = txNonce.add(new BigInteger("1"));
        }
    }

## 测试用例

[AuthorityServiceTest.java](/src/test/java/ai/bianjie/ddc/service/AuthorityServiceTest.java)

[ChargeServiceTest.java](/src/test/java/ai/bianjie/ddc/service/ChargeServiceTest.java)

[BaseServiceTest.java](/src/test/java/ai/bianjie/ddc/service/BaseServiceTest.java)

[BlockEventServiceTest.java](/src/test/java/ai/bianjie/ddc/service/BlockEventServiceTest.java)

[DDC721ServiceTest.java](/src/test/java/ai/bianjie/ddc/service/DDC721ServiceTest.java)

[DDC1155ServiceTest.java](/src/test/java/ai/bianjie/ddc/service/DDC1155ServiceTest.java)

[SignEventTest.java](/src/test/java/ai/bianjie/ddc/SignEventTest.java)

[DDCSdkClientTest.java](/src/test/java/ai/bianjie/ddc/DDCSdkClientTest.java)

