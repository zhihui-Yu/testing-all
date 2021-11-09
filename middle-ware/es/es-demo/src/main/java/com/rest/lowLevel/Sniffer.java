package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.sniff.ElasticsearchNodesSniffer;
import org.elasticsearch.client.sniff.NodesSniffer;
import org.elasticsearch.client.sniff.SniffOnFailureListener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author simple
 */
public class Sniffer {
    public static void main(String[] args) throws IOException {
        /**
         * Once a RestClient instance has been created as shown in Initialization, a Sniffer can be associated to it.
         * The Sniffer will make use of the provided RestClient to periodically (every 5 minutes by default)
         * fetch the list of current nodes from the cluster and update them by calling RestClient#setNodes.
         */
        final RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
        final org.elasticsearch.client.sniff.Sniffer sniffer =
            org.elasticsearch.client.sniff.Sniffer.builder(restClient).build();

        /**
         * It is important to close the Sniffer so that its background thread gets properly shutdown and all of its resources are released.
         * The Sniffer object should have the same lifecycle as the RestClient and get closed right before the client:
         */
//        sniffer.close();;
//        restClient.close();

        /**
         * The Sniffer updates the nodes by default every 5 minutes. This interval can be customized by providing it (in milliseconds) as follows:
         */
        final org.elasticsearch.client.sniff.Sniffer customSniffer = org.elasticsearch.client.sniff.Sniffer.builder(restClient).setSniffIntervalMillis(60000).build();

        /**
         * It is also possible to enable sniffing on failure, meaning that after each failure the nodes list gets updated straightaway
         * rather than at the following ordinary sniffing round. In this case a SniffOnFailureListener needs to be created at first and provided at RestClient creation.
         * Also once the Sniffer is later created, it needs to be associated with that same SniffOnFailureListener instance,
         * which will be notified at each failure and use the Sniffer to perform the additional sniffing round as described.
         */

        final SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener(); // 1. Set the failure listener to the RestClient instance
        final RestClient client = RestClient.builder(new HttpHost("localhost", 9200))
            .setFailureListener(sniffOnFailureListener).build();
        final org.elasticsearch.client.sniff.Sniffer failureListenerSniffer = org.elasticsearch.client.sniff.Sniffer.builder(client)
            //When sniffing on failure, not only do the nodes get updated after each failure, but an additional sniffing round is also scheduled sooner than usual, by default one minute after the failure, assuming that things will go back to normal and we want to detect that as soon as possible. Said interval can be customized at Sniffer creation time through the setSniffAfterFailureDelayMillis method. Note that this last configuration parameter has no effect in case sniffing on failure is not enabled like explained above.
            .setSniffAfterFailureDelayMillis(30000).build();
        sniffOnFailureListener.setSniffer(failureListenerSniffer); // 3. Set the Sniffer instance to the failure listener

        /**
         * The Elasticsearch Nodes Info api doesn’t return the protocol to use when connecting to the nodes but only their host:port key-pair,
         * hence http is used by default. In case https should be used instead, the ElasticsearchNodesSniffer instance has to be manually created and provided as follows:
         */
        final ElasticsearchNodesSniffer nodesSniffer = new ElasticsearchNodesSniffer(
            restClient,
            ElasticsearchNodesSniffer.DEFAULT_SNIFF_REQUEST_TIMEOUT,
            ElasticsearchNodesSniffer.Scheme.HTTPS);
        final org.elasticsearch.client.sniff.Sniffer httpsSniffer = org.elasticsearch.client.sniff.Sniffer.builder(restClient)
            .setNodesSniffer(nodesSniffer).build();

        /**
         * In the same way it is also possible to customize the sniffRequestTimeout, which defaults to one second.
         * That is the timeout parameter provided as a query string parameter when calling the Nodes Info api,
         * so that when the timeout expires on the server side, a valid response is still returned although
         * it may contain only a subset of the nodes that are part of the cluster, the ones that have responded until then.
         */
        final ElasticsearchNodesSniffer nodesSniffer1 = new ElasticsearchNodesSniffer(
            restClient,
            TimeUnit.SECONDS.toMillis(5),
            ElasticsearchNodesSniffer.Scheme.HTTP);
        org.elasticsearch.client.sniff.Sniffer.builder(restClient)
            .setNodesSniffer(nodesSniffer1).build();

        /**
         * Also, a custom NodesSniffer implementation can be provided for advanced use cases that may require fetching
         * the nodes from external sources rather than from Elasticsearch:
         */
        NodesSniffer nodesSniffer2 =  new NodesSniffer(){

            @Override
            public List<Node> sniff() throws IOException {
                return null;// Fetch the hosts from the external source
            }
        };
        org.elasticsearch.client.sniff.Sniffer.builder(restClient).setNodesSniffer(nodesSniffer2).build();
    }
}
