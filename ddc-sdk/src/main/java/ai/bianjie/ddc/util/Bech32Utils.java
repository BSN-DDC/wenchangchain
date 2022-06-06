package ai.bianjie.ddc.util;

import org.bitcoinj.core.Bech32;
import org.bouncycastle.util.encoders.Hex;

public class Bech32Utils {
    private Bech32Utils() {
    }

    /**
     * 以太坊的账户地址转为指定前缀的bech32编码的链账户地址
     *
     * @param hrp       目标链账户地址的前缀
     * @param pubkeyHex 字节格式的以太坊的链账户地址
     * @return {@link String}
     */
    public static String toBech32(String hrp, byte[] pubkeyHex) {
        byte[] bits = AddressUtils.convertBits(pubkeyHex, 0, pubkeyHex.length, 8, 5, true);
        return Bech32.encode(Bech32.Encoding.BECH32, hrp, bits);
    }

    /**
     * 以太坊的账户地址转为指定前缀的bech32编码的链账户地址
     *
     * @param address bech32编码的链账户地址
     * @return {@link byte[]} 字节格式的bech32编码的链账户地址
     */
    public static byte[] fromBech32(String address) {
        Bech32.Bech32Data data = Bech32.decode(address);
        return AddressUtils.convertBits(data.data, 0, data.data.length, 5, 8, true);
    }

    /**
     * bech32编码的链账户地址转化为以太坊格式的地址
     *
     * @param address bech32编码的链账户地址
     * @return {@link String} 以太坊格式的地址
     */
    public static String bech32ToHex(String address) {
        return Hex.toHexString(fromBech32(address)).toUpperCase();
    }

    /**
     * 以太坊的账户地址转为指定前缀的bech32编码的链账户地址
     *
     * @param hrp        目标链账户地址的前缀
     * @param hexAddress 字符串格式的以太坊的链账户地址
     * @return {@link String} 目标链账户地址
     */
    public static String hexToBech32(String hrp, String hexAddress) {
        return toBech32(hrp, Hex.decode(hexAddress));
    }
}
