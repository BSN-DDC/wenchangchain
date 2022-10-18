package ai.bianjie.ddc.util;

import org.bitcoinj.core.Bech32;
import org.bouncycastle.util.encoders.Hex;

public class Bech32Utils {
    private Bech32Utils() {
    }

    /**
     * The account address of Ethereum is converted to the bech32 encoded chain account address of the specified prefix
     *
     * @param hrp       target chain account address prefix
     * @param pubkeyHex chain account address of Ethereum in byte format
     * @return {@link String}
     */
    public static String toBech32(String hrp, byte[] pubkeyHex) {
        byte[] bits = AddressUtils.convertBits(pubkeyHex, 0, pubkeyHex.length, 8, 5, true);
        return Bech32.encode(Bech32.Encoding.BECH32, hrp, bits);
    }

    /**
     * The account address of Ethereum is converted to the bech32 encoded chain account address of the specified prefix
     *
     * @param address bech32 encoded chain account address
     * @return {@link byte[]} bech32 encoded chain account address in byte format
     */
    public static byte[] fromBech32(String address) {
        Bech32.Bech32Data data = Bech32.decode(address);
        return AddressUtils.convertBits(data.data, 0, data.data.length, 5, 8, true);
    }

    /**
     * The bech32 encoded chain account address is converted into an address in Ethereum format
     *
     * @param address bech32 encoded chain account address
     * @return {@link String} address in ethereum format
     */
    public static String bech32ToHex(String address) {
        return Hex.toHexString(fromBech32(address)).toUpperCase();
    }

    /**
     * The account address of Ethereum is converted to the bech32 encoded chain account address of the specified prefix
     *
     * @param hrp        target chain account address prefix
     * @param hexAddress the chain account address of Ethereum in string format
     * @return {@link String} target chain account address
     */
    public static String hexToBech32(String hrp, String hexAddress) {
        return toBech32(hrp, Hex.decode(hexAddress));
    }
}
