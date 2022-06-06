package ai.bianjie.ddc.service;

import ai.bianjie.ddc.contract.DDC721;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import org.web3j.utils.Strings;


import java.math.BigInteger;


public class DDC721Service extends BaseService {
    private final DDC721 ddc721;

    public DDC721Service(SignEventListener signEventListener) {
        //注册签名事件
        super.signEventListener = signEventListener;
        //获取合约实体
        this.ddc721 = Web3jUtils.getDDC721();
    }

    /**
     * Generate DDC
     * The platform party or end user can generate DDC by calling this method.
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param ddcURI DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String mint(String sender, String to, String ddcURI) throws Exception {
        //1.检查sender是否为标准备address格式
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        //2.检查接收者账户地址是否为空
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        //3.检查接收者账户地址格式是否正确
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        //4.检查ddcURI是否为空
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }

        //5.获取序列化编码后的方法
        String encodedFunction = ddc721.mint(to, ddcURI).encodeFunctionCall();
        //6.签名并发送，最终返回交易hash
        return signAndSend(ddc721, DDC721.FUNC_MINT, encodedFunction, signEventListener, sender).getTransactionHash();

    }

    /**
     * Safely generate DDC
     * The platform party or end user can generate DDC safely by calling this method.
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param ddcURI DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMint(String sender, String to, String ddcURI, byte[] data) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }

        String encodedFunction = ddc721.safeMint(to, ddcURI, data).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SAFEMINT, encodedFunction, signEventListener, sender).getTransactionHash();

    }

    /**
     * DDC authorization
     * The DDC owner can authorize the DDC by calling this method, and the initiator needs to be the owner of the DDC.
     *
     * @param sender Caller address
     * @param to     Authorizer account
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String approve(String sender, String to, BigInteger ddcId) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.approve(to, ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_APPROVE, encodedFunction, signEventListener, sender).getTransactionHash();
    }


    /**
     * DDC authorization query
     * The operator, the platform or the end user can query the authorization status of the DDC by calling this method.
     *
<<<<<<< HEAD
     * @param ddcId DDC unique identifier
     * @return Authorized Account
=======
     * @param ddcId DDC唯一标识
     * @return 授权的账户
>>>>>>> 3c79aa6bb5cdfea77ee38603cba817a3d3f04b3b
     * @throws Exception Exception
     */
    public String getApproved(BigInteger ddcId) throws Exception {

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        return Web3jUtils.getDDC721().getApproved(ddcId).send();
    }

    /**
     * Account authorization
     * The DDC owner can authorize the account by calling this method, and the initiator needs to be the owner of the DDC.
     *
     * @param sender   Caller address
     * @param operator Authorizer account
     * @param approved Authorization ID
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String setApprovalForAll(String sender, String operator, Boolean approved) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        String encodedFunction = ddc721.setApprovalForAll(operator, approved).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SETAPPROVALFORALL, encodedFunction, signEventListener, sender).getTransactionHash();
    }


    /**
     * Account authorization query
     * Operators, platform parties or end users can call this method to query account authorization.
     *
     * @param owner    Owner account
     * @param operator Authorizer account
     * @return Authorization ID
     * @throws Exception Exception
     */
    public Boolean isApprovedForAll(String owner, String operator) throws Exception {

        if (Strings.isEmpty(owner) || Strings.isEmpty(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(owner) || !AddressUtils.isValidAddress(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        return Web3jUtils.getDDC721().isApprovedForAll(owner, operator).send();
    }


    /**
     * Safe transfer of DDC
     * The owner or authorizer of DDC can transfer DDC by calling this method.
     *
     * @param sender Caller address
     * @param from   Owner account
     * @param to     Authorizer account
     * @param ddcId  DDC unique identifier
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeTransferFrom(String sender, String from, String to, BigInteger ddcId, byte[] data) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.safeTransferFrom(from, to, ddcId, data).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SAFETRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }


    /**
     * Transfer
     * The DDC owner or authorizer can transfer DDC by calling this method.
     *
     * @param sender Caller address
     * @param from   Owner account
     * @param to     Recipient account
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String transferFrom(String sender, String from, String to, BigInteger ddcId) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.transferFrom(from, to, ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_TRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }


    /**
     * Freeze
     * The operator can freeze the DDC by calling this method.
     *
     * @param sender Caller address
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String freeze(String sender, BigInteger ddcId) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.freeze(ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_FREEZE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Thaw
     * The operator can unfreeze the DDC by calling this method.
     *
     * @param sender Caller address
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String unFreeze(String sender, BigInteger ddcId) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.unFreeze(ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_UNFREEZE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Burn
     * DDC owner or DDC authorizer can destroy DDC by calling this method
     *
     * @param sender Caller address
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String burn(String sender, BigInteger ddcId) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.burn(ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_BURN, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Number of queries
     * Operators, platform parties and end users can query the number of DDCs owned by the current account by calling this method.
     *
     * @param owner Owner account
     * @return Number of DDCs
     * @throws Exception Exception
     */
    public BigInteger balanceOf(String owner) throws Exception {
        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        return Web3jUtils.getDDC721().balanceOf(owner).send();
    }

    /**
     * Query owner
     * Operators, platform parties and end users can query the current DDC owner by calling this method.
     *
     * @param ddcId  DDC unique identifier
     * @return Owner account
     * @throws Exception Exception
     */
    public String ownerOf(BigInteger ddcId) throws Exception {

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }
        return Web3jUtils.getDDC721().ownerOf(ddcId).send();
    }

    /**
     * Get name
     * Operators, platform parties and end users can query the name of the current DDC by calling this method.
     *
     * @return DDC operator name
     * @throws Exception Exception
     */
    public String name() throws Exception {
        return Web3jUtils.getDDC721().name().send();
    }

    /**
     * Get symbol
     * The operator, the platform and the end user can query the current DDC symbol by calling this method.
     *
     * @return DDC operator symbol
     * @throws Exception Exception
     */
    public String symbol() throws Exception {
        return Web3jUtils.getDDC721().symbol().send();
    }

    /**
     * Get ddcURI
     * The operator, the platform and the end user can query the resource identifier of the current DDC by calling this method.
     *
     * @param ddcId DDC Unique Identifier
     * @return DDC resource identifier
     * @throws Exception Exception
     */
    public String ddcURI(BigInteger ddcId) throws Exception {

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }
        return Web3jUtils.getDDC721().ddcURI(ddcId).send();
    }

    /**
     * URI settings
     * The DDC owner or DDC authorizer sets the resource identifier of the DDC by calling this method.
     *
     * @param sender Caller address
     * @param ddcId  DDC Unique Identifier
     * @param ddcURI  DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String setURI(String sender, BigInteger ddcId, String ddcURI) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }

        String encodedFunction = ddc721.setURI(ddcId, ddcURI).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SETURI, encodedFunction, signEventListener, sender).getTransactionHash();
    }
}
