package ai.bianjie.ddc.listener;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Numeric;

public class sign implements SignEventListener {
    @Override
    public String signEvent(SignEvent signEvent) {
        Credentials credentials = Credentials.create("17AAB5FD9E5D21C4ED53B39832723F56A0BC195D2C27566742349A4035406CD8");
        byte[] signMessage = TransactionEncoder.signMessage(signEvent.rawTransaction, credentials);
        return Numeric.toHexString(signMessage);
    }
}
