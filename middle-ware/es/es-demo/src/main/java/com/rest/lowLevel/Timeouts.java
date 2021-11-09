package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;

/**
 * @author simple
 */
public class Timeouts {
    public static void main(String[] args) {
        /**
         * Configuring requests timeouts can be done by providing an instance of RequestConfigCallback while building the RestClient through its builder.
         * The interface has one method that receives an instance of org.apache.http.client.config.RequestConfig.
         * Builder as an argument and has the same return type. The request config builder can be modified and then returned.
         * In the following org.example we increase the connect timeout (defaults to 1 second) and the socket timeout (defaults to 30 seconds).
         */
        RestClient.builder(
            new HttpHost("localhost", 9200))
            .setRequestConfigCallback(requestConfigBuilder ->
//              equals ->  new RestClientBuilder.RequestConfigCallback()
                requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000)
            );

        /**
         * Timeouts also can be set per request with RequestOptions, which overrides RestClient customizeRequestConfig.
         */
        final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000)
            .setSocketTimeout(60000).build();
        final RequestOptions requestOptions = RequestOptions.DEFAULT.toBuilder().setRequestConfig(requestConfig).build();
    }
}
