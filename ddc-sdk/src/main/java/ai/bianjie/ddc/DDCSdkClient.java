package ai.bianjie.ddc;


import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.service.*;

public class DDCSdkClient {
    private String opbGateWebAddress;
    private String gasPrice;
    private String gasLimit;
    private String ddc721Address;
    private String ddc1155Address;
    private String authorityLogicAddress;
    private String chargeLogicAddress;
    private SignEventListener signEventListener;
    private String headerKey;
    private String headerValue;

    /**
     * SDK 初始化方法
     */
    private DDCSdkClient(Builder builder) {
        this.opbGateWebAddress = builder.opbGateWebAddress;
        this.gasPrice = builder.gasPrice;
        this.gasLimit = builder.gasLimit;
        this.ddc721Address = builder.ddc721Address;
        this.ddc1155Address = builder.ddc1155Address;
        this.authorityLogicAddress = builder.authorityLogicAddress;
        this.chargeLogicAddress = builder.chargeLogicAddress;
        this.signEventListener = builder.signEventListener;
        this.headerKey = builder.headerKey;
        this.headerValue = builder.headerValue;
    }

    public static class Builder {
        private String opbGateWebAddress;
        private String gasPrice;
        private String gasLimit;
        private String ddc721Address;
        private String ddc1155Address;
        private String authorityLogicAddress;
        private String chargeLogicAddress;
        private SignEventListener signEventListener;
        private String headerKey;
        private String headerValue;
        public Builder(){
        }


        public Builder setGasPrice(String gasPrice) {
            this.gasPrice = gasPrice;
            return this;
        }

        public Builder setGasLimit(String gasLimit) {
            this.gasLimit = gasLimit;
            return this;
        }

        public Builder setDDC721Address(String ddc721Address) {
            this.ddc721Address = ddc721Address;
            return this;
        }

        public Builder setDDC1155Address(String ddc1155Address) {
            this.ddc1155Address = ddc1155Address;
            return this;
        }

        public Builder setAuthorityLogicAddress(String authorityLogicAddress) {
            this.authorityLogicAddress = authorityLogicAddress;
            return this;
        }

        public Builder setChargeLogicAddress(String chargeLogicAddress) {
            this.chargeLogicAddress = chargeLogicAddress;
            return this;
        }

        public Builder setSignEventListener(SignEventListener signEventListener) {
            if (signEventListener == null) {
                throw new DDCException(ErrorMessage.NO_SIGN_EVENT_LISTNER);
            }
            this.signEventListener = signEventListener;
            return this;
        }

        public DDCSdkClient init() {
            ConfigCache.initCache(opbGateWebAddress, headerKey, headerValue, gasPrice, gasLimit, ddc721Address, ddc1155Address, authorityLogicAddress, chargeLogicAddress);
            return new DDCSdkClient(this);
        }
    }

    public Boolean setGatewayUrl(String gatewayUrl){
        if(gatewayUrl.isEmpty()){
            return false;
        }
        ConfigCache.get().setOpbGatewayAddress(gatewayUrl);
        return true;
    }

    public Boolean setGatewayApiKey(String apiKey){
        if(apiKey.isEmpty()){
            return false;
        }
        ConfigCache.get().setHeaderKey(apiKey);
        return true;
    }




    /**
     * 获取权限管理服务的示例
     *
     * @return 返回权限管理服务的实例
     */
    public AuthorityService getAuthorityService() {
        return new AuthorityService(signEventListener);
    }

    /**
     * 获取费用管理服务的实例
     *
     * @return 返回费用管理服务的实例
     */
    public ChargeService getChargeService() {
        return new ChargeService(signEventListener);
    }

    /**
     * 获取BSN-DDC-1155合约服务的实例
     *
     * @return 返回BSN-DDC-1155合约服务的实例
     */
    public DDC1155Service getDDC1155Service() {
        return new DDC1155Service(signEventListener);
    }

    /**
     * 获取BSN-DDC-721合约服务的实例
     *
     * @return 返回BSN-DDC-721合约服务的实例
     */
    public DDC721Service getDDC721Service() {
        return new DDC721Service(signEventListener);
    }

}
