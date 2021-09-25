package com.javatechie.twilio.resource;
import com.javatechie.twilio.resource.handler.TwilioSMSHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private TwilioSMSHandler twilioSMSHandler;

    @Bean
    public RouterFunction<ServerResponse> senOTP() {
        return RouterFunctions.route()
                .POST("/router/sendOTP",twilioSMSHandler::sendOTP)
                .POST("/router/validateOTP", twilioSMSHandler::validateOTP)
                .build();
    }
}
