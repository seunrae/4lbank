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
public class UserTransactionDto {
    String accountNumber;
    BigDecimal amount;
    String userDescription;
    String pin;
}
