package com.rest.lowLevel;

import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;

/**
 * @author simple
 */
public class RequestOption {
    /**
     * The RequestOptions class holds parts of the request
     * that should be shared between many requests in the same application.
     * You can make a singleton instance and share it between all requests:
     */

    private static final RequestOptions OPTIONS;

    static {
        final RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.addHeader("Authorization", "TOKEN");
        builder.setHttpAsyncResponseConsumerFactory(
            // Customize the response consumer.
            new HttpAsyncResponseConsumerFactory
                .HeapBufferedResponseConsumerFactory(10 * 1024 * 1024));
        OPTIONS = builder.build();
    }
}
