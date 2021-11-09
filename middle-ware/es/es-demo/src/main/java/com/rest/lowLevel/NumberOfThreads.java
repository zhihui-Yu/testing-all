package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

/**
 * @author simple
 */
public class NumberOfThreads {
    public static void main(String[] args) {
        /**
         * The Apache Http Async Client starts by default one dispatcher thread, and a number of worker threads used by the connection manager,
         * as many as the number of locally detected processors (depending on what Runtime.getRuntime().availableProcessors() returns).
         * The number of threads can be modified as follows:
         */
        RestClient.
            builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultIOReactorConfig(
                        IOReactorConfig.custom().setIoThreadCount(1).build()
                    );
                }
            });
    }
}
