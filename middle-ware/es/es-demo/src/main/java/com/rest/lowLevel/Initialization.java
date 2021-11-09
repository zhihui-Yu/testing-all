package com.rest.lowLevel;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * @author simple
 */
public class Initialization {
    public static void main(String[] args) throws IOException {
        /**
         * A RestClient instance can be built through the corresponding RestClientBuilder class,
         * created via RestClient#builder(HttpHost...) static method.
         * The only required argument is one or more hosts that the client will communicate with, provided as instances of HttpHost as follows:
         */

        RestClient client = RestClient.builder(
            new HttpHost("localhost", 9200, "http"),
            new HttpHost("localhost", 9201, "http")
        ).build();

        /**
         * The RestClient class is thread-safe and ideally has the same lifecycle as
         * the application that uses it. It is important that it gets closed
         * when no longer needed so that all the resources used by it get properly released,
         * as well as the underlying http client instance and its threads:
         */

        client.close();

        final RestClientBuilder clientBuilder = RestClient.builder(
            new HttpHost("localhost", 9201, "http")
        );

        /**
         * RestClientBuilder also allows to optionally set the following
         * configuration parameters while building the RestClient instance:
         *
         * Set the default headers that need to be sent with each request,
         * to prevent having to specify them with each single request
         */

        Header[] headers = {new BasicHeader("head", "value")};

        clientBuilder.setDefaultHeaders(headers);


        /**
         * Set a listener that gets notified every time a node fails,
         * in case actions need to be taken. Used internally when sniffing on failure is enabled.
         */

        clientBuilder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
            }
        });

        /**
         * Set the node selector to be used to filter the nodes
         * the client will send requests to among the ones that are set to the client itself.
         * This is useful for instance to prevent sending requests to dedicated master nodes
         * when sniffing is enabled. By default the client sends requests to every configured node.
         */

        clientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);

        /**
         *
         * Set a callback that allows to modify the default request configuration
         * (e.g. request timeouts, authentication,
         * or anything that the org.apache.http.client.config.RequestConfig.Builder allows to set)
         */

        clientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                // todo something like modify request config
                return requestConfigBuilder.setConnectTimeout(10000);
            }
        });

        /**
         *
         * Set a callback that allows to modify the http client configuration
         * (e.g. encrypted communication over ssl, or anything
         * that the org.apache.http.impl.nio.client.HttpAsyncClientBuilder allows to set)
         */

        clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                // todo can do anything HttpAsyncClientBuilder allows to set
                return httpClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"));
            }
        });

    }
}
