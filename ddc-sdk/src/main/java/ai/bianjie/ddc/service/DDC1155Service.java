package ai.bianjie.ddc.service;

import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.contract.DDC1155;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import com.google.common.collect.Multimap;
import org.web3j.utils.Strings;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class DDC1155Service extends BaseService {
    private final DDC1155 ddc1155;

    public DDC1155Service(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.ddc1155 = Web3jUtils.getDDC1155();
    }

    /**
     * Safely generate DDC
     * The platform party or end user can generate DDC securely by calling this method.
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param amount Number of DDCs
     * @param ddcURI DDC resource identifier
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMint(String sender, String to, BigInteger amount, String ddcURI, byte[] data) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }

        String encodedFunction = ddc1155.safeMint(to, amount, ddcURI, data).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SAFEMINT, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Batch Safe Generation
     * The platform party or the end user can perform batch safe generation of DDC by calling this method.
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param ddcs   DDC information
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMintBatch(String sender, String to, Multimap<BigInteger, String> ddcs, byte[] data) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        List<BigInteger> amounts = new ArrayList<>();
        List<String> ddcURIS = new ArrayList<>();

        if (ddcs == null || ddcs.size() <= 0) {
            throw new DDCException(ErrorMessage.ERR_ACCOUNTS_SIZE);
        }

        ddcs.forEach((amount, ddcURI) -> {
            if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
            }
            if (Strings.isEmpty(ddcURI)) {
                throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
            }
            amounts.add(amount);
            ddcURIS.add(ddcURI);

        });

        String encodedFunction = ddc1155.safeMintBatch(to, amounts, ddcURIS, data).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SAFEMINTBATCH, encodedFunction, signEventListener, sender).getTransactionHash();
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

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(operator)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        String encodedFunction = ddc1155.setApprovalForAll(operator, approved).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SETAPPROVALFORALL, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Account authorization query
     * Operators, platform parties or end users can call this method to query account authorization.
     *
     * @param owner    Owner account
     * @param operator Authorizer account
     * @return Authorization result（boolean）
     * @throws Exception Exception
     */
    public Boolean isApprovedForAll(String owner, String operator) throws Exception {

        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (Strings.isEmpty(operator)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (!AddressUtils.isValidAddress(operator)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        return Web3jUtils.getDDC1155().isApprovedForAll(owner, operator).send();
    }

    /**
     * Safe transfer
     * DDC owner or DDC authorizer can transfer DDC by calling this method.
     *
     * @param sender Caller address
     * @param from   Owner account
     * @param to     Recipient account
     * @param ddcId  DDCID
     * @param amount The amount of DDC to transfer
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeTransferFrom(String sender, String from, String to, BigInteger ddcId, BigInteger amount, byte[] data) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(from) || !AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }

        String encodedFunction = ddc1155.safeTransferFrom(from, to, ddcId, amount, data).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SAFETRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Bulk safe transfer
     * The DDC owner or DDC authorizer can transfer DDC in batches by calling this method.
     *
     * @param sender Caller address
     * @param from   Owner account
     * @param to     Recipient account
     * @param ddcs   Owner's ddcID collection
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeBatchTransferFrom(String sender, String from, String to, Multimap<BigInteger, BigInteger> ddcs, byte[] data) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(from) || !AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcs == null || ddcs.size() <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        ArrayList<BigInteger> ddcIds = new ArrayList();
        ArrayList<BigInteger> amounts = new ArrayList();
        ddcs.forEach((ddcId, amount) -> {
            if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
            }
            if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
            }
            ddcIds.add(ddcId);
            amounts.add(amount);
        });

        String encodedFunction = ddc1155.safeBatchTransferFrom(from, to, ddcIds, amounts, data).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SAFEBATCHTRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Burn
     * DDC owner can destroy DDC by calling this method.
     *
     * @param sender Caller address
     * @param owner  Owner account
     * @param ddcId  DDC unique identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String burn(String sender, String owner, BigInteger ddcId) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc1155.burn(owner, ddcId).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_BURN, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Bulk destruction
     * DDC owners can destroy DDC in batches by calling this method.
     *
     * @param sender Caller address
     * @param owner  Owner account
     * @param ddcIds Collection of DDC unique identifiers
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String burnBatch(String sender, String owner, List<BigInteger> ddcIds) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcIds == null || ddcIds.size() <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        ddcIds.forEach((ddcId) -> {
            if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
            }
        });

        String encodedFunction = ddc1155.burnBatch(owner, ddcIds).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_BURNBATCH, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Number of queries
     * Operators, platform parties and end users can query the number of DDCs owned by the current account by calling this method.
     *
     * @param owner Owner account
     * @param ddcId DDC unique identifier
     * @return The number of corresponding ddcIds owned by the owner account
     * @throws Exception
     */
    public BigInteger balanceOf(String owner, BigInteger ddcId) throws Exception {

        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        return Web3jUtils.getDDC1155().balanceOf(owner, ddcId).send();
    }

    /**
     * The number of batch queries
     * Operators, platforms and end users can call this method to query the number of DDCs owned by an account in batches.
     *
     * @param ddcs Owner ddcID collection
     * @return The number of corresponding ddcIds owned by the owner account
     * @throws Exception
     */
    public List balanceOfBatch(Multimap<String, BigInteger> ddcs) throws Exception {

        if (ddcs == null || ddcs.size() == 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        ArrayList<String> owners = new ArrayList<>();
        ArrayList<BigInteger> ddcIds = new ArrayList<>();

        ddcs.forEach((owner, ddcId) -> {

            if (Strings.isEmpty(owner)) {
                throw new DDCException(ErrorMessage.ACC_ADDR_IS_EMPTY);
            }

            if (!AddressUtils.isValidAddress(owner)) {
                throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
            }

            if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
            }

            owners.add(owner);
            ddcIds.add(ddcId);
        });

        return Web3jUtils.getDDC1155().balanceOfBatch(owners, ddcIds).send();
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

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        return Web3jUtils.getDDC1155().ddcURI(ddcId).send();
    }

    /**
     * URI settings
     * The DDC owner or DDC authorizer sets the resource identifier of the DDC by calling this method.
     *
     * @param sender Caller address
     * @param owner  Owner
     * @param ddcId  DDC Unique Identifier
     * @param ddcURI DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String setURI(String sender, String owner, BigInteger ddcId, String ddcURI) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(owner)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.OWNER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }

        String encodedFunction = ddc1155.setURI(owner, ddcId, ddcURI).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_SETURI, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Query nonce
     * Query the latest nonce value corresponding to the signer's account.
     * Note: this query is only applicable to the nonce value query corresponding to the initiating meta transaction.
     *
     * @return Latest Nonce
     * @throws Exception Exception
     */
    public BigInteger getNonce(String from) throws Exception {
        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        return ddc1155.getNonce(from).send();
    }

    /**
     * Mint DDC meta transaction securely
     * The platform side and end user call this API to generate DDC meta transaction securely
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param amount   Number of DDCs
     * @param ddcURI   DDC resource identifier
     * @param data     Additional data
     * @param nonce    1 more than the actual nonce of the signer
     * @param deadline The expiration time must be a timestamp equal to 0 or greater than or equal to the block
     * @param sign     Signed transaction
     * @return hash    Transaction hash
     * @throws Exception Exception
     */
    public String metaSafeMint(String sender, String to, BigInteger amount, String ddcURI, byte[] data, BigInteger nonce, BigInteger deadline, byte[] sign) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }

        String encodedFunction = ddc1155.metaSafeMint(to, amount, ddcURI, data, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_METASAFEMINT, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Transfer DDC meta transaction securely
     * The platform side and end user call this API to transfer DDC meta transaction securely
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param ddcId    DDC Unique Identifier
     * @param amount   Number of DDCs to be transferred
     * @param data     Additional data
     * @param nonce    1 more than the actual nonce of the signer
     * @param deadline The expiration time must be a timestamp equal to 0 or greater than or equal to the block
     * @param sign     Signed transaction
     * @return hash    Transaction hash
     * @throws Exception Exception
     */
    public String metaSafeTransferFrom(String sender, String from, String to, BigInteger ddcId, BigInteger amount, byte[] data, BigInteger nonce, BigInteger deadline, byte[] sign) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }

        String encodedFunction = ddc1155.metaSafeTransferFrom(from, to, ddcId, amount, data, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_METASAFETRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Burn DDC meta transaction securely
     * The platform side and end user call this API to burn DDC meta transaction securely
     *
     * @param sender   Caller address
     * @param owner    Owner of DDC
     * @param ddcId    DDC Unique Identifier
     * @param nonce    1 more than the actual nonce of the signer
     * @param deadline The expiration time must be a timestamp equal to 0 or greater than or equal to the block
     * @param sign     Signed transaction
     * @return hash    Transaction hash
     * @throws Exception Exception
     */
    public String metaBurn(String sender, String owner, BigInteger ddcId, BigInteger nonce, BigInteger deadline, byte[] sign) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (!AddressUtils.isValidAddress(owner)) {
            throw new DDCException(ErrorMessage.OWNER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc1155.metaBurn(owner, ddcId, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc1155, DDC1155.FUNC_METABURN, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Generate securely generate DDC
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param amount Number of DDCs
     * @param ddcURI DDC resource identifier
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMintHash(String sender, String to, BigInteger amount, String ddcURI, byte[] data) {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }
        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }
        String encodedFunction = ddc1155.safeMint(to, amount, ddcURI, data).encodeFunctionCall();
        return generateOfflineHash(ddc1155, DDC1155.FUNC_SAFEMINT, encodedFunction, signEventListener, sender);
    }
}