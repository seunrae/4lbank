package com.example.seun4L._LBank.transaction.dto;

import com.example.seun4L._LBank.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TransactionResponseDto {
    private Long transactionId;
    private String description;
    private String amount;
    private String userDescription;
    private String accountNumber;
    private String accountName;
    private String accountBalance;
    private String transactionType;
    private String status;
    private String referenceNumber;
    private LocalDate createdAt;
}
