package ai.bianjie.ddc.service;

import ai.bianjie.ddc.contract.DDC721;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import org.web3j.utils.Strings;


import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DDC721Service extends BaseService {
    private final DDC721 ddc721;

    public DDC721Service(SignEventListener signEventListener) {
        /**
         * register signature event
         */
        super.signEventListener = signEventListener;
        /**
         * Get the contract entity
         */
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

        // Get the serialized encoded method
        String encodedFunction = ddc721.mint(to, ddcURI).encodeFunctionCall();
        // Sign and send, and finally return the transaction hash
        return signAndSend(ddc721, DDC721.FUNC_MINT, encodedFunction, signEventListener, sender).getTransactionHash();

    }

    /**
     * Safely generate DDC
     * The platform party or end user can generate DDC safely by calling this method.
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param ddcURI DDC resource identifier
     * @param data   Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMint(String sender, String to, String ddcURI, byte[] data) throws Exception {
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

        String encodedFunction = ddc721.safeMint(to, ddcURI, data).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SAFEMINT, encodedFunction, signEventListener, sender).getTransactionHash();

    }

    /**
     * Generate DDC in batches
     * The platform party or end user can generate DDC in batches by calling this method.
     *
     * @param sender  Caller address
     * @param to      Recipient account
     * @param ddcURIs DDC resource identifier list
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String mintBatch(String sender, String to, List<String> ddcURIs) throws Exception {

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

        // Get the serialized encoded method
        String encodedFunction = ddc721.mintBatch(to, ddcURIs).encodeFunctionCall();
        // Sign and send, and finally return the transaction hash
        return signAndSend(ddc721, DDC721.FUNC_MINTBATCH, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Safely generate DDC in batches
     * The platform party or end user can generate DDC safely in batches by calling this method.
     *
     * @param sender  Caller address
     * @param to      Recipient account
     * @param ddcURIs DDC resource identifier list
     * @param data    Additional data
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String safeMintBatch(String sender, String to, List<String> ddcURIs, byte[] data) throws Exception {
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

        String encodedFunction = ddc721.safeMintBatch(to, ddcURIs, data).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SAFEMINTBATCH, encodedFunction, signEventListener, sender).getTransactionHash();
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

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.approve(to, ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_APPROVE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * DDC authorization query
     * The operator, the platform or the end user can query the authorization status of the DDC by calling this method.
     *
     * @param ddcId DDC unique identifier
     * @return Authorized Account
     * @throws Exception Exception
     */
    public String getApproved(BigInteger ddcId) throws Exception {

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
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

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
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

        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
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

        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        String encodedFunction = ddc721.transferFrom(from, to, ddcId).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_TRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
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

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
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
     * @param ddcId DDC unique identifier
     * @return Owner account
     * @throws Exception Exception
     */
    public String ownerOf(BigInteger ddcId) throws Exception {

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
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

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
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
     * @param ddcURI DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String setURI(String sender, BigInteger ddcId, String ddcURI) throws Exception {

        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }

        String encodedFunction = ddc721.setURI(ddcId, ddcURI).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_SETURI, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Generate DDC offline hash
     *
     * @param sender Caller address
     * @param to     Recipient account
     * @param ddcURI DDC resource identifier
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String mintHash(String sender, String to, String ddcURI) {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
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
        String encodedFunction = ddc721.mint(to, ddcURI).encodeFunctionCall();
        return generateOfflineHash(ddc721, DDC721.FUNC_MINT, encodedFunction, signEventListener, sender);
    }

    /**
     * The platform party or end user can generate meta-transactions for DDC by invoking this method by authorized users in the platform
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param ddcURI   DDC resource identifier
     * @param nonce    The nonce maintained in the DDC721 contract
     * @param deadline Expiration
     * @param sign     Signature value
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String metaMint(String sender, String to, String ddcURI, BigInteger nonce, BigInteger deadline, byte[] sign) throws ExecutionException, InterruptedException {
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
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }
        String encodedFunction = ddc721.metaMint(to, ddcURI, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_METAMINT, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The platform party or end user can safely generate meta-transactions for DDC by invoking this method by authorized users in the platform
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param ddcURI   DDC resource identifier
     * @param data     Additional data
     * @param nonce    The nonce maintained in the DDC721 contract
     * @param deadline Expiration
     * @param sign     Signature value
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String metaSafeMint(String sender, String to, String ddcURI, byte[] data, BigInteger nonce, BigInteger deadline, byte[] sign) throws ExecutionException, InterruptedException {
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
        if (Strings.isEmpty(ddcURI)) {
            throw new DDCException(ErrorMessage.DDCURI_IS_EMPTY);
        }
        String encodedFunction = ddc721.metaSafeMint(to, ddcURI, data, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_METASAFEMINT, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The DDC owner or DDC authorizer transfers the meta transaction to the DDC by calling this method on the authorized platform side
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param nonce    The nonce maintained in the DDC721 contract
     * @param deadline Expiration
     * @param sign     Signature value
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String metaTransferFrom(String sender,String from, String to, BigInteger ddcId, BigInteger nonce, BigInteger deadline, byte[] sign) throws ExecutionException, InterruptedException {
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
        String encodedFunction = ddc721.metaTransferFrom(from, to, ddcId, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_METATRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The owner or authorizer of DDC can transfer DDC meta-transaction securely by invoking this method by authorized users in the platform
     *
     * @param sender   Caller address
     * @param to       Recipient account
     * @param ddcId    DDC Unique Identifier
     * @param data     Additional data
     * @param nonce    The nonce maintained in the DDC721 contract
     * @param deadline Expiration
     * @param sign     Signature value
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String metaSafeTransferFrom(String sender, String from, String to, BigInteger ddcId, byte[] data, BigInteger nonce, BigInteger deadline, byte[] sign) throws ExecutionException, InterruptedException {
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
        String encodedFunction = ddc721.metaSafeTransferFrom(from, to, ddcId, data, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_METASAFETRANSFERFROM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * DDC owners or DDC authorizers can call this method to destroy DDC meta-transactions by authorizing users in the platform
     *
     * @param sender   Caller address
     * @param ddcId    DDC Unique Identifier
     * @param nonce    The nonce maintained in the DDC721 contract
     * @param deadline Expiration
     * @param sign     Signature value
     * @return hash, Transaction hash
     * @throws Exception Exception
     */
    public String metaBurn(String sender, BigInteger ddcId, BigInteger nonce, BigInteger deadline, byte[] sign) throws ExecutionException, InterruptedException {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }
        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }
        String encodedFunction = ddc721.metaBurn(ddcId, nonce, deadline, sign).encodeFunctionCall();
        return signAndSend(ddc721, DDC721.FUNC_METABURN, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Call this method to query the latest nonce value corresponding to the signer's account.
     * Note: This query is only applicable to the query of the nonce value corresponding to
     * the initiation of the meta transaction processing business.
     *
     * @return nonce, The nonce maintained in the DDC721 contract
     * @throws Exception Exception
     */
    public BigInteger getNonce(String from) throws Exception {
        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_EMPTY);
        }
        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }
        return ddc721.getNonce(from).send();
    }
}
