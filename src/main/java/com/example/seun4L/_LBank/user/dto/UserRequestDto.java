package com.example.seun4L._LBank.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String pin;
    private String address;
    private String stateOfOrigin;
    private String country;
}
