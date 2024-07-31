package com.example.seun4L._LBank.user.dto;

import com.example.seun4L._LBank.bank.model.Bank;
import com.example.seun4L._LBank.user.model.ACCOUNTSTATUS;
import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import com.example.seun4L._LBank.user.model.ROLE;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserResponseDto {
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String address;
    private String stateOfOrigin;
    private String country;
    private ACCOUNTSTATUS accountStatus;
    private ACCOUNTTYPE accountType;
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
    private ROLE role;
}
