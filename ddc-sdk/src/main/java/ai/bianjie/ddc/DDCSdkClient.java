package ai.bianjie.ddc;


import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.constant.ErrorMessage;
import ai.bianjie.ddc.exception.DDCException;
import ai.bianjie.ddc.listener.SignEventListener;
import ai.bianjie.ddc.service.*;
import ai.bianjie.ddc.util.Web3jUtils;

public class DDCSdkClient {
    private SignEventListener signEventListener;

    /**
     * SDK initialization method
     */
    private DDCSdkClient(Builder builder) {
        this.signEventListener = builder.signEventListener;
    }

    public static class Builder {
        private String gasPrice;
        private String gasLimit;
        private String ddc721Address;
        private String ddc1155Address;
        private String authorityLogicAddress;
        private String chargeLogicAddress;
        private SignEventListener signEventListener;


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
            ConfigCache.initCache(gasPrice, gasLimit, ddc721Address, ddc1155Address, authorityLogicAddress, chargeLogicAddress);
            return new DDCSdkClient(this);
        }
    }

    public Boolean setGatewayUrl(String gatewayUrl) {
        if (gatewayUrl.isEmpty()) {
            return false;
        }
        ConfigCache.get().setOpbGatewayAddress(gatewayUrl);
        Web3jUtils.reset();
        return true;
    }

    public Boolean setGatewayApiKey(String apiKey) {
        if (apiKey.isEmpty()) {
            return false;
        }
        ConfigCache.get().setHeaderKey(apiKey);
        Web3jUtils.reset();
        return true;
    }

    public Boolean setGatewayApiValue(String apiValue) {
        if (apiValue.isEmpty()) {
            return false;
        }
        ConfigCache.get().setHeaderValue(apiValue);
        Web3jUtils.reset();
        return true;
    }
    public Boolean setConnectTimeout(long timeout) {
        if (timeout==0) {
            return false;
        }
        ConfigCache.get().setConnectTimeout(timeout);
        Web3jUtils.reset();
        return true;
    }

    /**
     * Example of obtaining rights management service
     *
     * @return returns the instance of the permission management service
     */
    public AuthorityService getAuthorityService() {
        return new AuthorityService(signEventListener);
    }

    /**
     * Get an instance of the expense management service
     *
     * @return returns the instance of the expense management service
     */
    public ChargeService getChargeService() {
        return new ChargeService(signEventListener);
    }

    /**
     * Get an instance of BSN-DDC-1155 contract service
     *
     * @return returns an instance of the BSN-DDC-1155 contract service
     */
    public DDC1155Service getDDC1155Service() {
        return new DDC1155Service(signEventListener);
    }

    /**
     * Get an instance of BSN-DDC-721 contract service
     *
     * @return returns an instance of the BSN-DDC-721 contract service
     */
    public DDC721Service getDDC721Service() {
        return new DDC721Service(signEventListener);
    }

}
