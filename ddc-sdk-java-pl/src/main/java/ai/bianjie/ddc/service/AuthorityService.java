package ai.bianjie.ddc.service;

import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.contract.Authority;
import ai.bianjie.ddc.dto.AccountInfo;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.utils.Strings;

import java.math.BigInteger;


public class AuthorityService extends BaseService {
    private Authority authority;
    private String encodedFunction;

    public AuthorityService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.authority = Web3jUtils.getAuthority();
    }

    /**
     * 运营方、平台方以及终端用户可以通过调用该方法进行DDC账户信息的查询。
     *
     * @param account DDC用户链账户地址
     * @return 返回DDC账户信息
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
        return new AccountInfo(res.getValue1(), res.getValue2(), res.getValue3().toString(), res.getValue4(), res.getValue5().toString(), res.getValue6().toString(), res.getValue7());
    }

    /**
     * 运营方或平台方可以通过调用该方法对终端用户进行DDC账户信息状态的更改。
     *
     * @param sender              调用者地址
     * @param account             DDC用户链账户地址
     * @param state               状态 ：Frozen - 冻结状态 ； Active - 活跃状态
     * @param changePlatformState 修改平台方状态标识
     * @return 返回交易哈希
     * @throws Exception
     */
    public String updateAccState(String sender, String account, BigInteger state, boolean changePlatformState) throws Exception {
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

        encodedFunction = authority.updateAccountState(account, state, changePlatformState).encodeFunctionCall();
        return signAndSend(authority, Authority.FUNC_UPDATEACCOUNTSTATE, encodedFunction, signEventListener, sender).getTransactionHash();
    }
}
