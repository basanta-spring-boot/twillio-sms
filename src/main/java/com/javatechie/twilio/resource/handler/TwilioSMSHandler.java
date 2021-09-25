package com.javatechie.twilio.resource.handler;

import com.javatechie.twilio.dto.PasswordRestRequest;
import com.javatechie.twilio.service.TwilioSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioSMSHandler {

    @Autowired
    private TwilioSMSService twilioSMSService;

    public Mono<ServerResponse> sendOTP(ServerRequest request) {
        return request.bodyToMono(PasswordRestRequest.class)
                .flatMap(dto -> twilioSMSService.sendOTPForResetPassword(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(dto)));
    }


    public Mono<ServerResponse> validateOTP(ServerRequest request) {
        return request.bodyToMono(PasswordRestRequest.class).
                flatMap(dto ->
                        twilioSMSService.validateOTP(dto.getMessage(), dto.getUserName()))
                .flatMap(resp -> ServerResponse.status(HttpStatus.ACCEPTED)
                        .bodyValue(resp));


    }

}
