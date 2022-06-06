package ai.bianjie.ddc.service;

import java.math.BigInteger;

import ai.bianjie.ddc.contract.Authority;
import org.web3j.utils.Strings;
import org.web3j.tuples.generated.Tuple7;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.dto.AccountInfo;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;


public class AuthorityService extends BaseService {
    private Authority authority;

    public AuthorityService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.authority = Web3jUtils.getAuthority();
    }

    /**
     * The operator can directly create the platform side or the end user of the platform side by calling this method.
     *
     * @param sender    Caller address
     * @param account   DDC chain account address
     * @param accName   The account name corresponding to the DDC account
     * @param accDID    DID information corresponding to the DDC account
     * @param leaderDID The DID of the superior account corresponding to the ordinary account
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String addAccountByOperator(String sender, String account, String accName, String accDID, String leaderDID) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(accName)) {
            throw new DDCException(ErrorMessage.ACCOUNT_NAME_IS_EMPTY);
        }

        String encodedFunction = authority.addAccountByOperator(account, accName, accDID, leaderDID).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_ADDACCOUNTBYOPERATOR, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Operators, platform parties and end users can query DDC account information by calling this method.
     *
     * @param account DDC user chain account address
     * @return AccountInfo, DDC Account Information
     * @throws Exception
     */
    public AccountInfo getAccount(String account) throws Exception {
        if (Strings.isEmpty(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        Tuple7<String, String, BigInteger, String, BigInteger, BigInteger, String> res = Web3jUtils.getAuthority().getAccount(account).send();
        return new AccountInfo(account, res.component1(), res.component2(), res.component3().toString(), res.component4(), res.component5().toString(), res.component6().toString(), res.component7());
    }

    /**
     * The operator or the platform can change the status of the DDC account information for the end user by calling this method.
     *
     * @param sender              Caller address
     * @param account             DDC user chain account address
     * @param state               Status: Frozen - Frozen; Active - Active
     * @param changePlatformState Modify the platform side status flag
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String updateAccState(String sender, String account, BigInteger state, boolean changePlatformState) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (state == null) {
            throw new DDCException(ErrorMessage.ACCOUNT_STASTUS_IS_EMPTY);
        }

        String encodedFunction = authority.updateAccountState(account, state, changePlatformState).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_UPDATEACCOUNTSTATE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator can authorize the cross-platform operation of DDC by calling this method.
     *
     * @param sender caller address
     * @param from authorizer
     * @param to receiver
     * @param approved authorization logo
     * @return returns the transaction hash
     * @throws Exception
     */
    public String crossPlatformApproval(String sender, String from, String to, Boolean approved) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(from)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(from)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        String encodedFunction = authority.crossPlatformApproval(from, to, approved).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_CROSSPLATFORMAPPROVAL, encodedFunction, signEventListener, sender).getTransactionHash();
    }

}
