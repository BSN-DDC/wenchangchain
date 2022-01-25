package ai.bianjie.ddc.dto;

public class Account {

    private String Keyseed;
    private String PubulicKey;
    private String PrivateKey;
    private String Address;

    public Account() {
    }

    public String getKeyseed() {
        return Keyseed;
    }

    public void setKeyseed(String keyseed) {
        Keyseed = keyseed;
    }

    public String getPubulicKey() {
        return PubulicKey;
    }

    public void setPubulicKey(String pubulicKey) {
        PubulicKey = pubulicKey;
    }

    public String getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(String privateKey) {
        PrivateKey = privateKey;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Account(String Keyseed, String PubulicKey, String PrivateKey, String Address) {
        this.Keyseed = Keyseed;
        this.PubulicKey = PubulicKey;
        this.PrivateKey = PrivateKey;
        this.Address = Address;
    }

}
