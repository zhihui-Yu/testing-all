package com.example;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author simple
 */
public class TwilioTest {
    // pls use yourself
    private static final String accountSid = "sid"; // Your Account SID from www.twilio.com/user/account
    private static final String authToken = "token"; // Your Auth Token from www.twilio.com/user/account

    public void contextLoads() {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
            new PhoneNumber("+86phone"),  // To number ,Phone number with area code
            new PhoneNumber("+1phone"),  // From number
            " Simple's test now."                   // SMS body
        ).create();

        if (message.getSid() != null && !message.getSid().isEmpty()) {
            System.out.println(message.getSid());
        }

    }


    public void sendCall() throws URISyntaxException {
        Twilio.init(accountSid, authToken);

        Call call = Call.creator(
            new PhoneNumber("+xxxx"),  // To number
            new PhoneNumber("+xxxx"),  // From number
            // Read TwiML at this URL when a call connects (hold music)
            new URI("http://twimlets.com/holdmusic?Bucket=com.twilio.music.ambient")
        ).create();

        if (call.getSid() != null && !call.getSid().isEmpty()) {
            System.out.println(call.getSid());
        }
    }
}
