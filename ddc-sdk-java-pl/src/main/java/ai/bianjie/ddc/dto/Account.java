package ai.bianjie.ddc.dto;

import java.math.BigInteger;
import java.util.List;

public class Account {

    private String Keyseed;
    private BigInteger PubulicKey;
    private BigInteger PrivateKey;
    private String Address;

    public Account() {
    }

    public String getKeyseed() {
        return Keyseed;
    }

    public void setKeyseed(String keyseed) {
        Keyseed = keyseed;
    }

    public BigInteger getPubulicKey() {
        return PubulicKey;
    }

    public void setPubulicKey(BigInteger pubulicKey) {
        PubulicKey = pubulicKey;
    }

    public BigInteger getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        PrivateKey = privateKey;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Account(String Keyseed, BigInteger PubulicKey, BigInteger PrivateKey, String Address) {
        this.Keyseed = Keyseed;
        this.PubulicKey = PubulicKey;
        this.PrivateKey = PrivateKey;
        this.Address = Address;
    }

}
