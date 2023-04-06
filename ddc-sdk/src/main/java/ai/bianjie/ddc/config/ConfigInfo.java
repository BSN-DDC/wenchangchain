package ai.bianjie.ddc.config;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ConfigInfo {
    private Map<String, String> funcGasLimitMap = new HashMap<>();

    /**
     * Offline maintenance user nonce
     */
    private BigInteger nonce;

    private String opbGatewayAddress = "http://192.168.150.43:8545";

    private String headerKey;

    private String headerValue;

    private long connectTimeout;

    private String gasPrice = "10000000";

    private String gasLimit = "0";

    private String funcGasLimit = "0";

    /**
     * authority contract
     */
    private String authorityLogicBIN;
    private String authorityLogicAddress = "0x368d4064762a22640E8a79cA6B62F0815f3e2F9C";

    /**
     * charge contract
     */
    private String chargeLogicBIN;
    private String chargeLogicAddress = "0xcA65BAbCB23053B0b1Fb3bB2d79E487e56e1FBC4";

    /**
     * DDC721 contract
     */
    private String ddc721BIN;
    private String ddc721Address = "0x02d40d287C851b760342126922f5D239321Dc4BC";

    /**
     * DDC1155 contract
     */
    private String ddc1155BIN;
    private String ddc1155Address = "0xf6Bc71043a176114A8E4cfF686D6F417b971d5bA";

    /**
     * OPBCrossChainApplied contract
     */
    private String opbCrossChainAppliedBIN;

    private String opbCrossChainAppliedAddress = "0xf6Bc71043a176114A8E4cfF686D6F417b971d5bA";

    public ConfigInfo() {
        /**
         * default 10s
         */
        connectTimeout = 10;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public String getOpbGatewayAddress() {
        return opbGatewayAddress;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public void setOpbGatewayAddress(String opbGatewayAddress) {
        this.opbGatewayAddress = opbGatewayAddress;
    }

    public String getDdc721Address() {
        return ddc721Address;
    }

    public void setDdc721Address(String ddc721Address) {
        this.ddc721Address = ddc721Address;
    }

    public String getDdc1155Address() {
        return ddc1155Address;
    }

    public void setDdc1155Address(String ddc1155Address) {
        this.ddc1155Address = ddc1155Address;
    }

    public String getOPBCrossChainAppliedAddress() {
        return opbCrossChainAppliedAddress;
    }

    public void setOPBCrossChainAppliedAddress(String opbCrossChainAppliedAddress) {
        this.opbCrossChainAppliedAddress = opbCrossChainAppliedAddress;
    }

    public String getAuthorityLogicAddress() {
        return authorityLogicAddress;
    }

    public void setAuthorityLogicAddress(String authorityLogicAddress) {
        this.authorityLogicAddress = authorityLogicAddress;
    }

    public String getChargeLogicAddress() {
        return chargeLogicAddress;
    }

    public void setChargeLogicAddress(String chargeLogicAddress) {
        this.chargeLogicAddress = chargeLogicAddress;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getDdc721BIN() {
        return ddc721BIN;
    }

    public void setDdc721BIN(String ddc721BIN) {
        this.ddc721BIN = ddc721BIN;
    }

    public String getDdc1155BIN() {
        return ddc1155BIN;
    }

    public void setDdc1155BIN(String ddc1155BIN) {
        this.ddc1155BIN = ddc1155BIN;
    }

    public String getAuthorityLogicBIN() {
        return authorityLogicBIN;
    }

    public void setAuthorityLogicBIN(String authorityLogicBIN) {
        this.authorityLogicBIN = authorityLogicBIN;
    }

    public String getChargeLogicBIN() {
        return chargeLogicBIN;
    }

    public String getOpbCrossChainAppliedBIN() {
        return opbCrossChainAppliedBIN;
    }

    public void setOpbCrossChainAppliedBIN(String opbCrossChainAppliedBIN) {
        this.opbCrossChainAppliedBIN = opbCrossChainAppliedBIN;
    }

    public String getFuncGasLimit() {
        return funcGasLimit;
    }

    public void setFuncGasLimit(String customerGasLimit) {
        this.funcGasLimit = customerGasLimit;
    }

    public void setChargeLogicBIN(String chargeLogicBIN) {
        this.chargeLogicBIN = chargeLogicBIN;
    }

    public Map<String, String> getFuncGasLimitMap() {
        return funcGasLimitMap;
    }

    public void setFuncGasLimitMap(Map<String, String> funcGasLimitMap) {
        this.funcGasLimitMap = funcGasLimitMap;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}