package com.rest.highlevel;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 */
public class IndexAPI {
    public static void main(String[] args) throws Exception {
        /**
         * An IndexRequest requires the following arguments:
         */
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
            "\"user\":\"kimchy\"," +
            "\"postDate\":\"2013-01-30\"," +
            "\"message\":\"trying out Elasticsearch\"" +
            "}";
        request.source(jsonString, XContentType.JSON);


        /**
         * The document source can be provided in different ways in addition to the String org.example shown above:
         */
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
            .id("1").source(jsonMap);


        /**
         * Document source provided as an XContentBuilder object, the Elasticsearch built-in helpers to generate JSON content
         */
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest2 = new IndexRequest("posts")
            .id("1").source(builder);

        /**
         * Document source provided as Object key-pairs, which gets converted to JSON format
         */
        IndexRequest indexRequest3 = new IndexRequest("posts")
            .id("1")
            .source("user", "kimchy",
                "postDate", new Date(),
                "message", "trying out Elasticsearch");

        /**
         * Routing value
         */
        request.routing("routing");

        /**
         * Timeout to wait for primary shard to become available as a TimeValue
         */
        request.timeout(TimeValue.timeValueSeconds(1));

        /**
         * Timeout to wait for primary shard to become available as a String
         */
        request.timeout("1s");

        /**
         * Refresh policy as a WriteRequest.RefreshPolicy instance
         */
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        /**
         * Refresh policy as a String
         */
        request.setRefreshPolicy("wait_for");

        /**
         * Version
         */
        request.version(2);

        /**
         * Version type
         */
        request.versionType(VersionType.EXTERNAL);

        /**
         * Operation type provided as an DocWriteRequest.OpType value
         */
        request.opType(DocWriteRequest.OpType.CREATE);

        /**
         * Operation type provided as a String: can be create or index (default)
         */
        request.opType("create");

        /**
         * The name of the ingest pipeline to be executed before indexing the document
         */
        request.setPipeline("pipeline");

        /**
         * Synchronous execution
         * When executing a IndexRequest in the following manner, the client waits for the IndexResponse to be returned before continuing with code execution:
         *
         * Synchronous calls may throw an IOException in case of either failing to parse the REST response in the high-level REST client,
         * the request times out or similar cases where there is no response coming back from the server.
         *
         * In cases where the server returns a 4xx or 5xx error code, the high-level client tries to parse the response body error details instead
         * and then throws a generic ElasticsearchException and adds the original ResponseException as a suppressed exception to it.
         */
        Node node = new Node(new HttpHost("localhost", 9200, null));
        RestClientBuilder clientBuilder = RestClient.builder(node);
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        /**
         * Asynchronous execution
         * Executing a IndexRequest can also be done in an asynchronous fashion so that the client can return directly.
         * Users need to specify how the response or potential failures will be handled by passing the request and a listener to the asynchronous index method:
         *
         * The IndexRequest to execute and the ActionListener to use when the execution completes
         *
         * The asynchronous method does not block and returns immediately. Once it is completed the ActionListener is called back using the onResponse method
         * if the execution successfully completed or using the onFailure method if it failed. Failure scenarios and expected exceptions are the same as in the synchronous execution case.
         */

        /**
         * A typical listener for index looks like:
         */
        var listener = new ActionListener<IndexResponse>() {
            // Called when the execution is successfully completed.
            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            // Called when the whole IndexRequest fails.
            @Override
            public void onFailure(Exception e) {

            }
        };
        client.indexAsync(request, RequestOptions.DEFAULT, listener);


        /**
         * Index Response
         * The returned IndexResponse allows to retrieve information about the executed operation as follows:
         *
         * Handle (if needed) the case where the document was created for the first time
         * Handle (if needed) the case where the document was rewritten as it was already existing
         * Handle the situation where number of successful shards is less than total shards
         * Handle the potential failures
         */
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {

        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {

        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }

        /**
         *
         * If there is a version conflict, an ElasticsearchException will be thrown:
         * The raised exception indicates that a version conflict error was returned
         */
        IndexRequest request5 = new IndexRequest("posts")
            .id("1")
            .source("field", "value")
            .setIfSeqNo(10L)
            .setIfPrimaryTerm(20);
        try {
            org.elasticsearch.action.index.IndexResponse response = client.index(request5, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        }

        /**
         * Same will happen in case opType was set to create and a document with same index and id already existed:
         */
        IndexRequest request6 = new IndexRequest("posts")
            .id("1")
            .source("field", "value")
            .opType(DocWriteRequest.OpType.CREATE);
        try {
            IndexResponse response = client.index(request6, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                // The raised exception indicates that a version conflict error was returned

            }
        }

    }
}
