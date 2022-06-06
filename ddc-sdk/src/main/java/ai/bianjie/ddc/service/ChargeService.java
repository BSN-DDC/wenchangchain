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

public class ChargeService extends BaseService {
    private Charge charge;

    public ChargeService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.charge = Web3jUtils.getCharge();
    }


    /**
     * The operator and the platform party call this interface to recharge the account of the same level or the lower-level account of the same party.
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
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }
        String encodedFunction = charge.recharge(to, amount).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_RECHARGE, encodedFunction, signEventListener, sender).getTransactionHash();
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

        return Web3jUtils.getCharge().balanceOf(accAddr).send();
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

        return Web3jUtils.getCharge().queryFee(ddcAddr, sigInByte).send();
    }

    /**
     * Operators can add business fees to their own accounts by calling this method.
     *
     * @param sender Caller address
     * @param amount Business fee for recharging the operator's account
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String selfRecharge(String sender, BigInteger amount) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }
        String encodedFunction = charge.selfRecharge(amount).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_SELFRECHARGE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator can set the method call fee of the specified DDC main contract by calling this method.
     *
     * @param sender  Caller address
     * @param ddcAddr DDC business contract address
     * @param sig     Contract method ID in Hex format
     * @param amount  Business expenses
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String setFee(String sender, String ddcAddr, String sig, BigInteger amount) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

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

        if (amount == null || amount.compareTo(new BigInteger(String.valueOf(0)))<=0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }
        byte[] sigInByte = Numeric.hexStringToByteArray(sig);
        String encodedFunction = charge.setFee(ddcAddr, sigInByte, amount).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_SETFEE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator can delete the method call fee of the specified DDC main contract by calling this method.
     *
     * @param sender  Caller address
     * @param ddcAddr DDC business contract address
     * @param sig     Contract method ID in Hex format
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String delFee(String sender, String ddcAddr, String sig) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

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
        String encodedFunction = charge.delFee(ddcAddr, sigInByte).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_DELFEE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * The operator can delete the specified DDC business contract authorization by calling this method.
     *
     * @param sender  Caller address
     * @param ddcAddr DDC business contract address
     * @return hash, Transaction hash
     * @throws Exception
     */
    public String delDDC(String sender, String ddcAddr) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(ddcAddr)) {
            throw new DDCException(ErrorMessage.DDC_ADDR_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(ddcAddr)) {
            throw new DDCException(ErrorMessage.DDC_ADDR_IS_NOT_ADDRESS_FORMAT);
        }

        String encodedFunction = charge.delDDC(ddcAddr).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_DELDDC, encodedFunction, signEventListener, sender).getTransactionHash();
    }

}