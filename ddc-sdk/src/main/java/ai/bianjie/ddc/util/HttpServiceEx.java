package ai.bianjie.ddc.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ai.bianjie.ddc.config.ConfigCache;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okio.Buffer;
import okio.BufferedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.http.HttpService;


/**
 * Implement Web3jService interface
 *
 * @author ysm
 * @date 2022/04/29
 */
public class HttpServiceEx extends HttpService {
    private static final CipherSuite[] INFURA_CIPHER_SUITES;
    private static final ConnectionSpec INFURA_CIPHER_SUITE_SPEC;
    private static final List<ConnectionSpec> CONNECTION_SPEC_LIST;
    public static final MediaType JSON_MEDIA_TYPE;
    public static final String DEFAULT_URL = "http://localhost:8545/";
    private static final Logger log;
    private OkHttpClient httpClient;
    private final String url;
    private final boolean includeRawResponse;
    private HashMap<String, String> headers;

    public HttpServiceEx(String url, OkHttpClient httpClient, boolean includeRawResponses) {
        super(includeRawResponses);
        this.headers = new HashMap();
        this.url = url;
        this.httpClient = httpClient;
        this.includeRawResponse = includeRawResponses;
    }

    public HttpServiceEx(OkHttpClient httpClient, boolean includeRawResponses) {
        this("http://localhost:8545/", httpClient, includeRawResponses);
    }

    public HttpServiceEx(String url, OkHttpClient httpClient) {
        this(url, httpClient, false);
    }

    public HttpServiceEx(String url) {
        this(url, createOkHttpClient());
    }

    public HttpServiceEx(String url, boolean includeRawResponse) {
        this(url, createOkHttpClient(), includeRawResponse);
    }

    public HttpServiceEx(OkHttpClient httpClient) {
        this("http://localhost:8545/", httpClient);
    }

    public HttpServiceEx(boolean includeRawResponse) {
        this("http://localhost:8545/", includeRawResponse);
    }

    public HttpServiceEx() {
        this("http://localhost:8545/");
    }

    private static OkHttpClient createOkHttpClient() {
        Builder builder = (new Builder()).connectionSpecs(CONNECTION_SPEC_LIST);
        configureLogging(builder);

        builder.connectionPool(new ConnectionPool())
                .connectTimeout(ConfigCache.get().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(ConfigCache.get().getConnectTimeout(), TimeUnit.SECONDS).build();
        return builder.build();
    }

    private static void configureLogging(Builder builder) {
        if (log.isDebugEnabled()) {
            Logger var10002 = log;
            var10002.getClass();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(var10002::debug);
            logging.setLevel(Level.BODY);
            builder.addInterceptor(logging);
        }

    }

    @Override
    protected InputStream performIO(String request) throws IOException {
        RequestBody requestBody = RequestBody.create(request, JSON_MEDIA_TYPE);
        Headers headers = this.buildHeaders();
        Request httpRequest = (new okhttp3.Request.Builder()).url(this.url).headers(headers).post(requestBody).build();
        Response response = this.httpClient.newCall(httpRequest).execute();
        this.processHeaders(response.headers());
        ResponseBody responseBody = response.body();
        if (response.isSuccessful()) {
            return responseBody != null ? this.buildInputStream(responseBody) : null;
        } else {
            int code = response.code();
            String text = responseBody == null ? "N/A" : responseBody.string();
            throw new ClientConnectionException("Invalid response received: " + code + "; " + text);
        }
    }

    @Override
    protected void processHeaders(Headers headers) {
    }

    private InputStream buildInputStream(ResponseBody responseBody) throws IOException {
        InputStream inputStream = responseBody.byteStream();
        if (this.includeRawResponse) {
            BufferedSource source = responseBody.source();
            source.request(9223372036854775807L);
            Buffer buffer = source.buffer();
            long size = buffer.size();
            if (size > 2147483647L) {
                throw new UnsupportedOperationException("Non-integer input buffer size specified: " + size);
            } else {
                int bufferSize = (int) size;
                BufferedInputStream bufferedinputStream = new BufferedInputStream(inputStream, bufferSize);
                bufferedinputStream.mark(inputStream.available());
                return bufferedinputStream;
            }
        } else {
            return inputStream;
        }
    }

    private Headers buildHeaders() {
        return Headers.of(this.headers);
    }

    @Override
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    @Override
    public void addHeaders(Map<String, String> headersToAdd) {
        this.headers.putAll(headersToAdd);
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void close() throws IOException {
    }

    static {
        INFURA_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256};
        INFURA_CIPHER_SUITE_SPEC = (new okhttp3.ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)).cipherSuites(INFURA_CIPHER_SUITES).build();
        CONNECTION_SPEC_LIST = Arrays.asList(INFURA_CIPHER_SUITE_SPEC, ConnectionSpec.CLEARTEXT);
        JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
        log = LoggerFactory.getLogger(org.web3j.protocol.http.HttpService.class);
    }
}
