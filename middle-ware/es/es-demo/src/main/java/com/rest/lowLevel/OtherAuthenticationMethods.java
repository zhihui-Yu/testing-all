package com.rest.lowLevel;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author simple
 */
public class OtherAuthenticationMethods {
    public static void main(String[] args) {
        /**
         * Elasticsearch Token Service
         * If you want the client to authenticate with an Elasticsearch access token, set the relevant HTTP request header.
         * If the client makes requests on behalf of a single user only, you can set the necessary Authorization header as a default header
         * as shown in the following org.example:
         */
        final RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200));
        final Header[] headers = {
            new BasicHeader("Authorization", "Bearer u6iuAxZ0RG1Kcm5jVFI4eU4tZU9aVFEwT2F3")
        };
        builder.setDefaultHeaders(headers);

        /**
         * Elasticsearch API
         * If you want the client to authenticate with an Elasticsearch API key, set the relevant HTTP request header.
         * If the client makes requests on behalf of a single user only, you can set the necessary Authorization header as a default header
         * as shown in the following org.example:
         */
        String apiKeyId = "uqlEyn8B_gQ_jlvwDIvM";
        String apiKeySecret = "HxHWk2m4RN-V_qg9cDpuX";
        String apiKeyAuth = Base64.getEncoder().encodeToString(
            (apiKeyId + ":" + apiKeySecret).getBytes(StandardCharsets.UTF_8));
        final Header[] defaultHeaders = {
            new BasicHeader("Authorization", "ApiKey " + apiKeyAuth)
        };
        builder.setDefaultHeaders(defaultHeaders);
    }
}
