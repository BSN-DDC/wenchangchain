package ai.bianjie.ddc.listener;

import org.web3j.crypto.RawTransaction;


public class SignEvent {
    String sender;
    RawTransaction rawTransaction;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public RawTransaction getRawTransaction() {
        return rawTransaction;
    }

    public void setRawTransaction(RawTransaction rawTransaction) {
        this.rawTransaction = rawTransaction;
    }
}
