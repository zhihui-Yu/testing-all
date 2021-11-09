package com.rest.lowLevel;


import org.apache.http.HttpHost;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author simple
 */
public class EncryptedCommunication {
    public static void main(String[] args) throws Exception {
        /**
         * Encrypted communication
         * Encrypted communication using TLS can also be configured through the HttpClientConfigCallback.
         * The org.apache.http.impl.nio.client.HttpAsyncClientBuilder received as an argument exposes multiple methods to configure encrypted communication:
         * `setSSLContext`, `setSSLSessionStrategy` and `setConnectionManager`, in order of precedence from the least important.
         *
         * When accessing an Elasticsearch cluster that is setup for TLS on the HTTP layer, the client needs to trust the certificate that Elasticsearch is using.
         * The following is an org.example of setting up the client to trust the CA that has signed the certificate that Elasticsearch is using,
         * when that CA certificate is available in a PKCS#12 keystore:
         */
        final Path trustStorePath = Paths.get("/path/to/truststore.p12");
        KeyStore trustStore = KeyStore.getInstance("pkcs12");
        String keyStorePass = "";
        try (InputStream is = Files.newInputStream(trustStorePath)) {
            trustStore.load(is, keyStorePass.toCharArray());
        }

        SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
        final SSLContext sslContext = sslContextBuilder.build();
        RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(sslContext));



        /**
         * The following is an org.example of setting up the client to trust the CA that has signed the certificate that Elasticsearch is using,
         * when that CA certificate is available as a PEM encoded file.
         */
        final Path caCertificatePath = Paths.get("/path/to/ca.crt");
        final CertificateFactory factory = CertificateFactory.getInstance("X.509");
        Certificate trustedCa;
        try (InputStream is = Files.newInputStream(caCertificatePath)) {
            trustedCa = factory.generateCertificate(is);
        }
        trustStore.load(null, null);
        trustStore.setCertificateEntry("ca", trustedCa);
        final SSLContext context = SSLContexts.custom().loadTrustMaterial(trustStore, null).build();
        RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(context));


        /**
         * When Elasticsearch is configured to require client TLS authentication, for org.example when a PKI realm is configured,
         * the client needs to provide a client certificate during the TLS handshake in order to authenticate.
         * The following is an org.example of setting up the client for TLS authentication with a certificate and a private key that are stored in a PKCS#12 keystore.
         */
        Path trustStoreRPath = Paths.get("/path/to/your/truststore.p12");
        Path keyStorePath = Paths.get("/path/to/your/keystore.p12");
        KeyStore trustedStore = KeyStore.getInstance("pkcs12");
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        String trustStorePass = "";
        try (InputStream is = Files.newInputStream(trustStoreRPath)) {
            trustedStore.load(is, trustStorePass.toCharArray());
        }
        try (InputStream is = Files.newInputStream(keyStorePath)) {
            keyStore.load(is, keyStorePass.toCharArray());
        }
        SSLContextBuilder sslBuilder = SSLContexts.custom()
            .loadTrustMaterial(trustedStore, null)
            .loadKeyMaterial(keyStore, keyStorePass.toCharArray());
        final SSLContext sslContextSimple = sslBuilder.build();
        RestClientBuilder builder = RestClient.builder(
            new HttpHost("localhost", 9200, "https"))
            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(sslContextSimple));

        /**
         * If the client certificate and key are not available in a keystore but rather as PEM encoded files, you cannot use them directly to build an SSLContext.
         * You must rely on external libraries to parse the PEM key into a PrivateKey instance. Alternatively, you can use external tools to build a keystore from your PEM files,
         * as shown in the following org.example:
         *
         * ``` openssl pkcs12 -export -in client.crt -inkey private_key.pem -name "client" -out client.p12 ```
         *
         * If no explicit configuration is provided, the system default configuration will be used.
         */
    }
}
