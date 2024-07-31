package com.example.seun4L._LBank.transaction.model;

import com.example.seun4L._LBank.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Table
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String description;
    private String amount;
    private String accountNumber;
    private String accountName;
    private String accountBalance;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;
    private String userDescription;
    private String transactionType;
    private String referenceNumber;
    private String status;
    @CreationTimestamp
    private LocalDate createdAt;
}
