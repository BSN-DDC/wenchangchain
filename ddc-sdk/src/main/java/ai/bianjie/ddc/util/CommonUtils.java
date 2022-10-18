package ai.bianjie.ddc.util;

import org.web3j.protocol.core.DefaultBlockParameter;

import java.math.BigInteger;

/**
 * @author ysm
 */
public class CommonUtils {
    private CommonUtils() {
    }

    /**
     * String converted to BigInteger
     *
     * @param val String type
     * @return BigInteger type
     */
    public static BigInteger string2BigInteger(String val) {
        boolean numeric00 = CommonUtils.isNumeric00(val);
        if (numeric00) {
            return BigInteger.valueOf(Long.valueOf(val));
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Determine if the string consists of numbers
     *
     * @param str string
     * @return judgment result
     */
    public static boolean isNumeric00(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Block height format conversion
     *
     * @param num block height
     * @return DefaultBlockParameter
     */
    public static DefaultBlockParameter getDefaultBlockParameter(String num) {
        if (isNumeric00(num)) {
            return DefaultBlockParameter.valueOf(BigInteger.valueOf(Long.parseLong(num)));
        } else {
            throw new NumberFormatException();
        }
    }

}
