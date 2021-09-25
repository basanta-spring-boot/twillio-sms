package com.javatechie.twilio.service;

import com.javatechie.twilio.config.TwilioConfig;
import com.javatechie.twilio.dto.PasswordRestRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioSMSService {

    @Autowired
    private TwilioConfig twilioConfig;

    Map<String, String> userOTPMap = new HashMap<>();

    public Mono<PasswordRestRequest> sendOTPForResetPassword(PasswordRestRequest passwordRestRequest) {
        String otp = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordRestRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            otp = generateOTP();
            String message = passwordRestRequest.getMessage() + ":" + otp;
            Message twilioMessage = Message
                    .creator(to, // to
                            from, // from
                            message)
                    .create();
            System.out.println(twilioMessage.getSid());
            userOTPMap.put(passwordRestRequest.getUserName(), otp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        passwordRestRequest.setMessage(otp);
        return Mono.justOrEmpty(passwordRestRequest);
    }

    public Mono<String> validateOTP(String otp, String userName) {
        if (otp.equals(userOTPMap.get(userName))) {
            userOTPMap.remove(userName, otp);
            return Mono.just("Successfully Validate user account please set new password");
        } else {
            return Mono.error(new IllegalArgumentException("Invalid OTP Please resend if required !"));
        }
    }

    private String generateOTP() {
        String otp = new DecimalFormat("000000")
                .format(new Random()
                        .nextInt(999999));
        return otp;

    }
}
