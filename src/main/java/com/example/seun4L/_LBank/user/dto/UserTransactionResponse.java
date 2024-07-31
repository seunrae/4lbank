package com.example.seun4L._LBank.user.dto;

import com.example.seun4L._LBank.user.model.ACCOUNTSTATUS;
import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import com.example.seun4L._LBank.user.model.ROLE;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserTransactionResponse {
    private String accountName;
    private String accountNumber;
    private String amount;
    private BigDecimal accountBalance;
    private String transactionType;
    private String status;
}
