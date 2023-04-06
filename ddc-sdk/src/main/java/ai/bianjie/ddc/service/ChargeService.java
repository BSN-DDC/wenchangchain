package ai.bianjie.ddc.service;

import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.contract.Charge;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.HexUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import java.math.BigInteger;
import java.util.List;

public class ChargeService extends BaseService {
    private Charge charge;

    public ChargeService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.charge = Web3jUtils.getCharge();
    }

    /**
     * The operator and the platform party call this interface to recharge the account of the same level
     * or the lower-level account of the same party.
     *
     * @param sender Caller address
     * @param to     The address of the top-up account
     * @param amount Recharge amount
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String recharge(String sender, String to, BigInteger amount) throws Exception {
        if (Strings.isEmpty(sender)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
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

        String encodedFunction = charge.recharge(to, amount).encodeFunctionCall();
        return signAndSend(charge, Charge.FUNC_RECHARGE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator or the platform party can recharge in batches for the same-level account
     * or the lower-level account belonging to the same party by calling this method.
     *
     * @param sender  Caller address
     * @param toList  Receiver account set
     * @param amounts Recharge amount set
     * @throws Exception
     */
    public String rechargeBatch(String sender, List<String> toList, List<BigInteger> amounts) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_NOT_STANDARD_ADDRESS_FORMAT);
        }

        if (toList.size() == 0 || toList == null) {
            throw new DDCException(ErrorMessage.ERR_ACCOUNTS_SIZE);
        }

        if (amounts.size() == 0 || amounts == null) {
            throw new DDCException(ErrorMessage.ERR_AMOUNTS_SIZE);
        }

        if (toList.size() != amounts.size()) {
            throw new DDCException(ErrorMessage.ERR_TOLIST_ACCOUNTS_SIZE_MISMATCH);
        }

        toList.forEach(to -> {
            if (Strings.isEmpty(to)) {
                throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
            }
            if (!AddressUtils.isValidAddress(to)) {
                throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
            }
        });

        amounts.forEach(amount -> {
            if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
                throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
            }
        });

        String encodedFunction = charge.rechargeBatch(toList, amounts).encodeFunctionCall();
        return signAndSend(charge, Charge.FUNC_RECHARGEBATCH, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator, platform or end user can call the method to query the balance of the specified account.
     *
     * @param accAddr Query account address
     * @return balance, The business fee balance corresponding to the account
     * @throws Exception
     */
    public BigInteger balanceOf(String accAddr) throws Exception {

        if (Strings.isEmpty(accAddr)) {
            throw new DDCException(ErrorMessage.ACC_ADDR_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(accAddr)) {
            throw new DDCException(ErrorMessage.ACC_ADDR_IS_NOT_ADDRESS_FORMAT);
        }

        return charge.balanceOf(accAddr).send();
    }

    /**
     * Operators, platforms or end users can call methods to query account balances in batches.
     *
     * @param accAddrs Account address collection
     * @return balance, The business fee balance corresponding to the account
     * @throws Exception
     */
    public List<BigInteger> balanceOfBatch(List<String> accAddrs) throws Exception {

        if (accAddrs == null || accAddrs.size() <= 0) {
            throw new DDCException(ErrorMessage.ACC_ADDR_IS_EMPTY);
        }

        accAddrs.forEach(address -> {
            if (Strings.isEmpty(address)) {
                throw new DDCException(ErrorMessage.ACC_ADDR_IS_EMPTY);
            }

            if (!AddressUtils.isValidAddress(address)) {
                throw new DDCException(ErrorMessage.ACC_ADDR_IS_NOT_ADDRESS_FORMAT);
            }
        });

        return charge.balanceOfBatch(accAddrs).send();
    }

    /**
     * The operator, the platform party or the end user can query the invocation service fee corresponding to the method of the specified DDC service contract by invoking this method.
     *
     * @param ddcAddr DDC business contract address
     * @param sig     Contract method ID in Hex format
     * @return Inquired DDC contract business fee
     * @throws Exception
     */
    public BigInteger queryFee(String ddcAddr, String sig) throws Exception {

        if (Strings.isEmpty(ddcAddr)) {
            throw new DDCException(ErrorMessage.DDC_ADDR_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(ddcAddr)) {
            throw new DDCException(ErrorMessage.DDC_ADDR_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(sig)) {
            throw new DDCException(ErrorMessage.SIG_IS_EMPTY);
        }

        if (!HexUtils.isValid4ByteHash(sig)) {
            throw new DDCException(ErrorMessage.SIG_IS_NOT_4BYTE_HASH);
        }
        byte[] sigInByte = Numeric.hexStringToByteArray(sig);

        return charge.queryFee(ddcAddr, sigInByte).send();
    }

    /**
     * Generate offline hash of top-up business fee
     *
     * @param sender Caller address
     * @param to     The address of the top-up account
     * @param amount Recharge amount
     * @throws Exception
     */
    public String rechargeHash(String sender, String to, BigInteger amount) {
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
        String encodedFunction = charge.recharge(to, amount).encodeFunctionCall();
        return generateOfflineHash(charge, Charge.FUNC_RECHARGE, encodedFunction, signEventListener, sender);
    }
}