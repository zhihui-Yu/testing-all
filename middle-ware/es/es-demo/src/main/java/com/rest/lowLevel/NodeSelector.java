package com.rest.lowLevel;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.util.Iterator;

/**
 * @author simple
 */
public class NodeSelector {
    public static void main(String[] args) {
        /**
         * The client sends each request to one of the configured nodes in round-robin fashion.
         * Nodes can optionally be filtered through a node selector that needs to be provided when initializing the client.
         * This is useful when sniffing is enabled, in case only dedicated master nodes should be hit by HTTP requests.
         * For each request the client will run the eventually configured node selector to filter the node candidates,
         * then select the next one in the list out of the remaining ones.
         */
        final RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setNodeSelector(nodes -> {
            /**
             * Set an allocation aware node selector that allows to pick a node in the local rack if any available,
             * otherwise go to any other node in any rack. It acts as a preference rather than a strict requirement,
             * given that it goes to another rack if none of the local nodes are available,
             * rather than returning no nodes in such case which would make the client forcibly revive a local node
             * whenever none of the nodes from the preferred rack is available.
             */
            boolean foundOne = false;
            for (Node node : nodes) {
                final String rackId = node.getAttributes().get("rack_id").get(0);
                if ("rack_one".equals(rackId)) {
                    foundOne = true;
                    break;
                }
            }
            if (foundOne) {
                final Iterator<Node> iterator = nodes.iterator();
                while (iterator.hasNext()) {
                    final Node node = iterator.next();
                    final String rackId = node.getAttributes().get("rack_id").get(0);
                    if (!"rack_one".equals(rackId)) {
                        iterator.remove();
                    }
                }
            }
        });

        /**
         * Warning:
         * Node selectors that do not consistently select the same set of nodes will make round-robin behaviour unpredictable and possibly unfair.
         * The preference org.example above is fine as it reasons about availability of nodes which already affects the predictability of round-robin.
         * Node selection should not depend on other external factors or round-robin will not work properly.
         */
    }
}
