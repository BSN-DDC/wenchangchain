package ai.bianjie.ddc.dto;

public class Account {

    private String keyseed;
    private String publicKey;
    private String privateKey;
    private String address;

    public Account() {
    }

    public String getKeyseed() {
        return keyseed;
    }

    public void setKeyseed(String keyseed) {
        this.keyseed = keyseed;
    }

    public String getPubulicKey() {
        return publicKey;
    }

    public void setPubulicKey(String pubulicKey) {
        this.publicKey = pubulicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Account(String keyseed, String publicKey, String privateKey, String address) {
        this.keyseed = keyseed;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.address = address;
    }

}
