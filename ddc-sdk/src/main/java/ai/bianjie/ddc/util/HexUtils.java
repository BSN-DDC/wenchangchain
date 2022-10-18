package ai.bianjie.ddc.util;

import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class HexUtils {
    private HexUtils() {
    }

    /**
     * Verify that the incoming parameter is a valid 4-bit Byte hash format string
     *
     * @param str validation string
     * @return returns the validation result, true or false.
     */
    public static boolean isValid4ByteHash(String str) {
        String strNoPrefix = Numeric.cleanHexPrefix(str);
        boolean valid = strNoPrefix.length() == 8;
        if (valid) {
            try {
                new BigInteger(strNoPrefix, 16);
            } catch (Exception e) {
                valid = false;
            }
        }
        return valid;
    }
}
