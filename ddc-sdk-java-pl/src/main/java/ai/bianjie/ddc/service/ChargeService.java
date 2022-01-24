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
    private String encodedFunction;

    public ChargeService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.charge = Web3jUtils.getCharge();
    }


    /**
     * ��Ӫ����ƽ̨�����øýӿ�Ϊ����ͬһ����ͬһ�����˻������¼��˻���ֵ��
     *
     * @param sender �����ߵ�ַ
     * @param to     ��ֵ�˻��ĵ�ַ
     * @param amount ��ֵ���
     * @return ���ؽ��׹�ϣ
     * @throws Exception
     */
    public String recharge(String sender, String to, BigInteger amount) throws Exception {
        if (!AddressUtils.isValidAddress(sender)) {
            throw new DDCException(ErrorMessage.SENDER_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (amount == null || amount.intValue() <= 0) {
            throw new DDCException(ErrorMessage.AMOUNT_IS_EMPTY);
        }
        encodedFunction = charge.recharge(to, amount).encodeFunctionCall();

        return signAndSend(charge, Charge.FUNC_RECHARGE, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * ��ѯָ���˻�����
     *
     * @param accAddr ��ѯ���˻���ַ
     * @return �����˻�����Ӧ��ҵ������
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
     * ��ѯָ����DDCҵ�����߼���Լ�ķ�������Ӧ�ĵ���ҵ����á�
     *
     * @param ddcAddr DDCҵ���Լ��ַ
     * @param sig     Hex��ʽ�ĺ�Լ����ID
     * @return ���ز�ѯ��DDC��Լҵ���
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
}
