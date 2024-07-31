package com.example.seun4L._LBank.user.dto;

import com.example.seun4L._LBank.bank.model.Bank;
import com.example.seun4L._LBank.user.model.ACCOUNTSTATUS;
import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String pin;
    private String address;
    private String stateOfOrigin;
    private String country;
    private String accountType;
    private BigDecimal accountBalance;
}
