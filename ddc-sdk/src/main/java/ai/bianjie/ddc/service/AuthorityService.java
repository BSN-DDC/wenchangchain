package ai.bianjie.ddc.service;

import java.math.BigInteger;
import java.util.List;

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
     * The platform side can create DDC account information by calling this method,
     * the upper-level role can operate the lower-level role account,
     * and the platform side can only add terminal accounts through this method.
     *
     * @param sender      Caller address
     * @param account     DDC chain account address
     * @param accountName The account name corresponding to the DDC account
     * @param accountDID  DID information corresponding to the DDC account
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String addAccountByPlatform(String sender, String account, String accountName, String accountDID) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(accountName)) {
            throw new DDCException(ErrorMessage.ACCOUNT_NAME_IS_EMPTY);
        }

        String encodedFunction = authority.addAccountByPlatform(account, accountName, accountDID).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_ADDACCOUNTBYPLATFORM, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The platform side can create batches of DDC chain account information by calling this method,
     * the upper-level role can operate the lower-level role account,
     * and the platform side can only add terminal accounts through this method.
     *
     * @param sender         Caller address
     * @param accountNames   Account name List
     * @param accountDIDs    Account did List
     * @param accountAddress Account address List
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String addBatchAccountByPlatform(String sender, List<String> accountNames, List<String> accountDIDs, List<String> accountAddress) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (accountNames == null || accountNames.size() <= 0) {
            throw new DDCException(ErrorMessage.ERR_NAMES_SIZE);
        }
        if (accountDIDs == null || accountDIDs.size() <= 0) {
            throw new DDCException(ErrorMessage.ERR_DIDS_SIZE);
        }
        if (accountAddress == null || accountAddress.size() <= 0) {
            throw new DDCException(ErrorMessage.ERR_ACCOUNTS_SIZE);
        }
        if (accountNames.size() != accountDIDs.size()) {
            throw new DDCException(ErrorMessage.ERR_NAME_DID_ADDRESS_SIZE);
        }
        if (accountNames.size() != accountAddress.size()) {
            throw new DDCException(ErrorMessage.ERR_NAME_DID_ADDRESS_SIZE);
        }

        accountAddress.forEach(address -> {
            if (Strings.isEmpty(address)) {
                throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
            }

            if (!AddressUtils.isValidAddress(address)) {
                throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
            }
        });

        accountNames.forEach(name -> {
            if (Strings.isEmpty(name)) {
                throw new DDCException(ErrorMessage.ACCOUNT_NAME_IS_EMPTY);
            }
        });

        String encodedFunction = authority.addBatchAccountByPlatform(accountAddress, accountNames, accountDIDs).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_ADDBATCHACCOUNTBYPLATFORM, encodedFunction, signEventListener, sender).getTransactionHash();
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
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(account)) {
            throw new DDCException(ErrorMessage.ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        String encodedFunction = authority.updateAccountState(account, state, changePlatformState).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_UPDATEACCOUNTSTATE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The platform can call this method to query the switch status of the added chain account on the platform side.
     *
     * @return switch status
     * @throws Exception
     */
    public Boolean switcherStateOfPlatform() throws Exception {
        return Web3jUtils.getAuthority().switcherStateOfPlatform().send();
    }
}
