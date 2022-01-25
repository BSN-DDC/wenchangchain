package ai.bianjie.ddc.util.crypto;

//import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.web3j.crypto.ECKeyPair;

public interface ISignHandle {

    public byte[] sign(byte[] data) throws Exception;

    public boolean verify(byte[] data, byte[] mac) throws Exception;

    public byte[] hash(byte[] data);

    public String getAddress() throws Exception;

    public ECKeyPair getKeyPair();

    public int getEncryptType();
}
