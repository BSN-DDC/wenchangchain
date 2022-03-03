package ai.bianjie.ddc;

import ai.bianjie.ddc.listener.SignEventListener;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Numeric;

public class SignEventTest implements SignEventListener {

    @Override
    @Test
    public String signEvent(ai.bianjie.ddc.listener.SignEvent signEvent) {
        Credentials credentials = Credentials.create("17AAB5FD9E5D21C4ED53B39832723F56A0BC195D2C27566742349A4035406CD8");
        byte[] signMessage = TransactionEncoder.signMessage(signEvent.getRawTransaction(), credentials);
        return Numeric.toHexString(signMessage);
    }
}
