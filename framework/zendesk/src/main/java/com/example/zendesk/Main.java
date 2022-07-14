package com.example.zendesk;

import com.zendesk.sunshine_conversations_client.ApiClient;
import com.zendesk.sunshine_conversations_client.ApiException;
import com.zendesk.sunshine_conversations_client.Configuration;
import com.zendesk.sunshine_conversations_client.api.MessagesApi;
import com.zendesk.sunshine_conversations_client.auth.HttpBearerAuth;
import com.zendesk.sunshine_conversations_client.model.Author;
import com.zendesk.sunshine_conversations_client.model.MessagePost;
import com.zendesk.sunshine_conversations_client.model.MessagePostResponse;
import com.zendesk.sunshine_conversations_client.model.TextMessage;

/**
 * @author simple
 */
public class Main {
    // refer to https://docs.smooch.io/rest/
    public static void main(String[] args) {
        String jwt = "eyAraWQiOiJhcHBfNjFlOGY1MDZhMjk3OGEwDGViMmJjNjg5IiwiYWxnIjoiSFM1MTIifQ.awogICJzY29wZSI6ICJhcHAiLAogICJleHAiOiAxNzU1Nzc5MDM5Cn0K.AyTk52ak7Wh_-wGacfujY7qEWIkUx-TxFby42B8LEnApYFB-QW0F4dTzOrNGP1yxsR7RrfUoU5VXo4AODtrgcg"; // expired
        String appId = "51151fa3c948df00d35ffaad"; // String | Identifies the app.
        String conversationId = "2300abfa5e9b091d842c5106"; // String | Identifies the conversation.


        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.smooch.io");

        // Configure HTTP basic authorization: basicAuth
//        HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
//        basicAuth.setUsername("API_KEY_ID");
//        basicAuth.setPassword("API_KEY_SECRET");

        // Uncomment this section to use JWTs instead
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken(jwt);

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        MessagePost messagePost = new MessagePost(); // MessagePost |


        // Add required body parameters
        messagePost.author(new Author().type(Author.TypeEnum.BUSINESS));
        messagePost.content(new TextMessage().text("hello"));
        try {
            MessagePostResponse result = apiInstance.postMessage(messagePost, appId, conversationId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#postMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
