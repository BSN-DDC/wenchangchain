package ai.bianjie.ddc.service;


import ai.bianjie.ddc.constant.CrossChainStateEnum;
import ai.bianjie.ddc.constant.DDCTypeEnum;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.contract.OPBCrossChainApplied;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.util.AddressUtils;
import ai.bianjie.ddc.util.Web3jUtils;
import org.web3j.utils.Strings;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class OPBCrossChainAppliedService extends BaseService {
    private final OPBCrossChainApplied opbCrossChainApplied;

    public OPBCrossChainAppliedService(SignEventListener signEventListener) {
        super.signEventListener = signEventListener;
        this.opbCrossChainApplied = Web3jUtils.getOPBCrossChainApplied();
    }

    /**
     * DDC cross chain transfer.
     * The DDC owner or authorizer can call the DDC cross chain application contract to
     * conduct the cross chain flow of DDC through this method.
     *
     * @param sender    Caller
     * @param ddcType   DDC Type Enumeration Value
     * @param ddcId     DDC unique identification.
     * @param isLock    Whether locked.
     * @param toChainID Target side chain ID.
     * @param to        Target chain recipient account.
     * @param data      Additional data.
     * @return hash
     * @throws Exception error
     */
    public String crossChainTransfer(String sender, DDCTypeEnum ddcType, BigInteger ddcId, Boolean isLock, BigInteger toChainID, String to, byte[] data) throws Exception {
        checkSender(sender);

        if (Strings.isEmpty(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_EMPTY);
        }

        if (!AddressUtils.isValidAddress(to)) {
            throw new DDCException(ErrorMessage.TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT);
        }

        if (ddcId == null || ddcId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.DDCID_IS_WRONG);
        }

        if (toChainID == null || toChainID.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.TO_CHAIN_ID_IS_EMPTY);
        }

        if (Objects.isNull(ddcType)) {
            throw new DDCException(ErrorMessage.DDC_TYPE_IS_EMPTY);
        }

        if (data == null) {
            data = "0x".getBytes(StandardCharsets.UTF_8);
        }

        String encodedFunction = opbCrossChainApplied.crossChainTransfer(new BigInteger(ddcType.getType()), ddcId, isLock, toChainID, to, data).encodeFunctionCall();
        return signAndSend(opbCrossChainApplied, OPBCrossChainApplied.FUNC_CROSSCHAINTRANSFER, encodedFunction, signEventListener, sender).getTransactionHash();
    }

    /**
     * Cross chain rollback
     * Though this method,the operator can call the DDC cross chain application contract to roll back the DDC cross chain.
     *
     * @param sender       Caller
     * @param crossChainId Cross chain ID
     * @param state        State
     * @param remark       Modification status remarks.
     * @return hash
     * @throws Exception error
     */
    public String updateCrossChainStatus(String sender, BigInteger crossChainId, CrossChainStateEnum state, String remark) throws Exception {
        checkSender(sender);

        if (Strings.isEmpty(remark)) {
            throw new DDCException(ErrorMessage.REMARK_IS_EMPTY);
        }

        if (Objects.isNull(state)) {
            throw new DDCException(ErrorMessage.STATE_IS_NULL);
        }

        if (crossChainId == null || crossChainId.compareTo(new BigInteger(String.valueOf(0))) <= 0) {
            throw new DDCException(ErrorMessage.CROSS_CHAIN_ID_IS_EMPTY);
        }

        String encodedFunction = opbCrossChainApplied.updateCrossChainStatus(crossChainId, new BigInteger(state.getState()), remark).encodeFunctionCall();
        return signAndSend(opbCrossChainApplied, OPBCrossChainApplied.FUNC_UPDATECROSSCHAINSTATUS, encodedFunction, signEventListener, sender).getTransactionHash();
    }

}
