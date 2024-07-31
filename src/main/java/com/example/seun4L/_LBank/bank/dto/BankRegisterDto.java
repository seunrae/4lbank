package com.example.seun4L._LBank.bank.dto;

import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BankRegisterDto {
    private String bankName;
    private String email;
    private String phone;
    private String address;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String country;
}
