package com.javatechie.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRestRequest {

    private String userName;
    private  String phoneNumber; // destination
    private  String message;

}
