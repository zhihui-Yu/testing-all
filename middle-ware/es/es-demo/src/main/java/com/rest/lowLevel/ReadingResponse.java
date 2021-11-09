package com.rest.lowLevel;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author simple
 *
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-low-usage-responses.html
 */
public class ReadingResponse {
    public static void main(String[] args) throws IOException {
        Response response = RestClient.builder(new HttpHost("localhost", 9201, "http"))
            .build().performRequest(new Request("GET", "/"));
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
    }
}
