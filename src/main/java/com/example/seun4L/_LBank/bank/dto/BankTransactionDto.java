package com.example.seun4L._LBank.bank.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BankTransactionDto {
    String accountNumber;
    String bankCode;
    BigDecimal amount;
}
