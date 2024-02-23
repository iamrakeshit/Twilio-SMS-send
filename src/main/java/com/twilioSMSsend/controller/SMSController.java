package com.twilioSMSsend.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioSMSsend.dto.SMSRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;
    //          http://localhost:8080/api/send-sms
    @PostMapping("/api/send-sms")
    public ResponseEntity<String> sendSMS(@RequestBody SMSRequest smsRequest) {
        try {
            // Initialize Twilio client
            Twilio.init(accountSid, authToken);

            // Send SMS message
            Message message = Message.creator(
                            new PhoneNumber(smsRequest.getTo()),
                            new PhoneNumber(twilioPhoneNumber),
                            smsRequest.getMessage())
                    .create();

            return ResponseEntity.ok("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS: " + e.getMessage());
        }
    }
}



//        {
//        "to":"+917001440884",
//        "message":"hello"
//        }