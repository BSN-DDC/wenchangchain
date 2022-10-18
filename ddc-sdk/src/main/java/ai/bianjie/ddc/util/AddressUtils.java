package ai.bianjie.ddc.util;

import org.web3j.crypto.WalletUtils;
import org.bitcoinj.core.AddressFormatException;

import java.io.ByteArrayOutputStream;

/**
 * @author ysm
 */
public class AddressUtils {

    private AddressUtils() {
    }

    /**
     * Verify that the incoming parameter is a valid blockchain account address format
     *
     * @param address address
     * @return Returns the validation result, true or false.
     */
    public static boolean isValidAddress(String address) {
        return WalletUtils.isValidAddress(address);
    }

    /**
     * Helper for re-arranging bits into groups.
     *
     * @param in       array
     * @param inStart  start
     * @param inLen    array length
     * @param fromBits before conversion
     * @param toBits   after conversion
     *                 Whether @param pad is converted
     * @return return byte array
     * @throws AddressFormatException
     */
    public static byte[] convertBits(final byte[] in, final int inStart, final int inLen, final int fromBits,
                                     final int toBits, final boolean pad) throws AddressFormatException {
        int acc = 0;
        int bits = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream(64);
        final int maxv = (1 << toBits) - 1;
        final int max_acc = (1 << (fromBits + toBits - 1)) - 1;
        for (int i = 0; i < inLen; i++) {
            int value = in[i + inStart] & 0xff;
            if ((value >>> fromBits) != 0) {
                throw new AddressFormatException(
                        String.format("Input value '%X' exceeds '%d' bit size", value, fromBits));
            }
            acc = ((acc << fromBits) | value) & max_acc;
            bits += fromBits;
            while (bits >= toBits) {
                bits -= toBits;
                out.write((acc >>> bits) & maxv);
            }
        }
        if (pad) {
            if (bits > 0) {
                out.write((acc << (toBits - bits)) & maxv);
            }
        } else if (bits >= fromBits || ((acc << (toBits - bits)) & maxv) != 0) {
            throw new AddressFormatException("Could not convert bits, invalid padding");
        }
        return out.toByteArray();
    }
}
