package ai.bianjie.ddc;

import ai.bianjie.ddc.listener.SignEventListener;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Numeric;

import java.security.KeyStore;
import java.util.HashMap;

public class SignEventTest implements SignEventListener {
    HashMap<String, String> keyStore = new HashMap();

    public SignEventTest() {
        // operator
        keyStore.put(DDCSdkClientTest.operator, "E253AB375A5806FA331E7DB32EDE524BD7D998475A60C957806066F14F479C25");
        // platform
        keyStore.put(DDCSdkClientTest.platform, "ED43B9686AB520C896BC33A1461BAF163EDBF0DBC4D3199E77793A49B9BB2568");
        // consumer
        keyStore.put(DDCSdkClientTest.consumer, "B97BCE72ED2DFBAC1501605591ABDFA3D1DBAE4EB3ED990B7EEAB003478C3CD0");
    }

    @Override
    public String signEvent(ai.bianjie.ddc.listener.SignEvent signEvent) {
        Credentials credentials = Credentials.create(keyStore.get(signEvent.getSender()));
        byte[] signMessage = TransactionEncoder.signMessage(signEvent.getRawTransaction(), credentials);
        return Numeric.toHexString(signMessage);
    }
}
