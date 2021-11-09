package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author simple
 */
public class PerformingRequests {
    public static void main(String[] args) throws IOException {

        RestClient client = RestClient.builder(new HttpHost("localhost", 9201, "http")).build();

        /**
         *
         * Once the RestClient has been created,
         * requests can be sent by calling either performRequest or performRequestAsync.
         * performRequest is synchronous and will block the calling thread and return
         * the Response when the request is successful or throw an exception if it fails.
         * performRequestAsync is asynchronous and accepts a ResponseListener argument
         * that it calls with a Response when the request is successful or with an Exception if it fails.
         *
         * The HTTP method (GET, POST, HEAD, etc)
         * The endpoint on the server
         *
         * This is synchronous:
         */

        Request request = new Request("GET", "/");

        // You can add request parameters to the request object:
        request.addParameter("pretty", "true");

        // You can set the body of the request to any HttpEntity:
        request.setEntity(new NStringEntity("{\"json\":\"file\"}", ContentType.APPLICATION_JSON));

        // You can also set it to a String which will default to a ContentType of application/json.
        request.setJsonEntity("{\"json\":\"text\"}");

        Response response = client.performRequest(request);

        /**
         * And this is asynchronous:
         */

        final Cancellable cancellable = client.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                // todo Handle the response
            }

            @Override
            public void onFailure(Exception exception) {
                // todo Handle the failure
            }
        });

        cancellable.cancel();
    }
}
