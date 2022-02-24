package ai.bianjie.ddc.util;

import ai.bianjie.ddc.config.ConfigCache;
import ai.bianjie.ddc.contract.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Strings;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Web3jUtils {
    private Web3jUtils() {
    }

    private static ECKeyPair ecKeyPair;

    static {
        try {
            ecKeyPair = Keys.createEcKeyPair();
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public static Authority getAuthority() {
        return Authority.load(ConfigCache.get().getAuthorityLogicAddress(), Web3jUtils.getWeb3j(), Credentials.create(ecKeyPair), new GasProvider());
    }

    public static Charge getCharge() {
        return Charge.load(ConfigCache.get().getChargeLogicAddress(), Web3jUtils.getWeb3j(), Credentials.create(ecKeyPair), new GasProvider());
    }

    public static DDC1155 getDDC1155() {
        return DDC1155.load(ConfigCache.get().getDdc1155Address(), Web3jUtils.getWeb3j(), Credentials.create(ecKeyPair), new GasProvider());
    }

    public static DDC721 getDDC721() {
        return DDC721.load(ConfigCache.get().getDdc721Address(), Web3jUtils.getWeb3j(), Credentials.create(ecKeyPair), new GasProvider());
    }

    public static Web3j getWeb3j() {
        HttpService httpService = new HttpService(ConfigCache.get().getOpbGatewayAddress());
        if (!Strings.isEmpty(ConfigCache.get().getHeaderKey()) && !Strings.isEmpty(ConfigCache.get().getHeaderValue())) {
            httpService.addHeader(ConfigCache.get().getHeaderKey(), ConfigCache.get().getHeaderValue());
        }
        return Web3j.build(httpService);
    }
}
