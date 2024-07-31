package com.example.seun4L._LBank.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JwtResponseDto {
    private String accountNumber;
    private String email;
    private String accountType;
    private String token;
}
