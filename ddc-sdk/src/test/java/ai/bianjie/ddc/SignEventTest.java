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
        //运营方
//        Credentials credentials = Credentials.create("17AAB5FD9E5D21C4ED53B39832723F56A0BC195D2C27566742349A4035406CD8");
        //平台方
        Credentials credentials = Credentials.create("FFD0441F3AF6A83179BEC344FB02AE4187B38A41368F8015439EF4984C68E488");
//        Credentials credentials = Credentials.create("FD1E890B7B76DC1B39434BA65675C17628554EEA86EACBD2664EC9A2005B1D09");
        byte[] signMessage = TransactionEncoder.signMessage(signEvent.getRawTransaction(), credentials);
        return Numeric.toHexString(signMessage);
    }
}
