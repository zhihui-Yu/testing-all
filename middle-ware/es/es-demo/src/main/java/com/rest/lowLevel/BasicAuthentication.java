package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

/**
 * @author simple
 */
public class BasicAuthentication {
    public static void main(String[] args) {
        /**
         * Configuring basic authentication can be done by providing an HttpClientConfigCallback while building the RestClient through its builder.
         * The interface has one method that receives an instance of org.apache.http.impl.nio.client.HttpAsyncClientBuilder as an argument and
         * has the same return type. The http client builder can be modified and then returned.
         * In the following org.example we set a default credentials provider that requires basic authentication.
         */
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user", "password"));
        RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
            );

        /**
         * Preemptive Authentication can be disabled, which means that every request will be sent without authorization headers to see if it is accepted and,
         * upon receiving an HTTP 401 response, it will resend the exact same request with the basic authentication header. If you wish to do this,
         * then you can do so by disabling it via the HttpAsyncClientBuilder:
         */
        RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching(); // Disable preemptive authentication
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
    }
}
