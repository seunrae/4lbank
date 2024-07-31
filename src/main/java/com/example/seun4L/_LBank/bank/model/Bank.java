package com.example.seun4L._LBank.bank.model;

import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import com.example.seun4L._LBank.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bank_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bank_id;
    private String bankCode;
    private String bankName;
    private String email;
    private String phone;
    private String address;
    private String accountNumber;
    private BigDecimal accountBalance;
    private ACCOUNTTYPE accounttype;
    private String country;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<User> users;
}
