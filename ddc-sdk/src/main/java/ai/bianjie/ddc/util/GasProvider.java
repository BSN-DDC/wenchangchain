package ai.bianjie.ddc.util;

import ai.bianjie.ddc.config.ConfigCache;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

/**
 * Provide management of gasLimit and gasPrice
 *
 * @author ysm
 */
public class GasProvider implements ContractGasProvider {
    /**
     * Defaults
     */
    private String gasPrice = ConfigCache.get().getGasPrice();
    private String gasLimit = ConfigCache.get().getGasLimit();
    /**
     * custom value
     */
    private String funcGaslimit = ConfigCache.get().getFuncGasLimit();

    @Override
    public BigInteger getGasPrice(String s) {
        return CommonUtils.string2BigInteger(this.gasPrice);
    }

    @Override
    public BigInteger getGasPrice() {
        return CommonUtils.string2BigInteger(this.gasPrice);
    }

    @Override
    public BigInteger getGasLimit(String s) {
        if (!funcGaslimit.equals("0")) {
            ConfigCache.get().getFuncGasLimitMap().put(s, funcGaslimit);
            ConfigCache.get().setFuncGasLimit("0");
            return CommonUtils.string2BigInteger(ConfigCache.get().getFuncGasLimitMap().get(s));
        } else {
            String limit = ConfigCache.get().getFuncGasLimitMap().get(s);
            if (limit == null) {
                return new BigInteger(this.gasLimit);
            }

            BigInteger defaultLimit = CommonUtils.string2BigInteger(limit);
            if (defaultLimit.compareTo(new BigInteger(String.valueOf(0))) == 0) {
                return new BigInteger(this.gasLimit);
            }
            return defaultLimit;
        }
    }

    @Override
    public BigInteger getGasLimit() {
        return CommonUtils.string2BigInteger(this.gasLimit);
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }
}
