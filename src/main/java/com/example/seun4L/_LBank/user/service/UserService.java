package com.example.seun4L._LBank.user.service;

import com.example.seun4L._LBank.exception.UserNotFoundException;
import com.example.seun4L._LBank.user.dto.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    ResponseEntity<?> createUser(UserRegisterDto userRegisterDto);
    ResponseEntity<?> userLogin(UserLoginDto userLoginDto) throws UserNotFoundException;
    ResponseEntity<?> updateUser(UserRequestDto userRequestDto);
    ResponseEntity<?> deleteUser();
    ResponseEntity<?> getUserById();
    ResponseEntity<?> getUserByAccountNumber(String accountNumber);
    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> debitUserAccount(UserTransactionDto userTransactionDto);
    ResponseEntity<?> creditUserAccount(UserTransactionDto userTransactionDto);
    ResponseEntity<?> transferFunds(UserTransferDto userTransferDto);
    ResponseEntity<?> getAccountBalance (String accountNumber);

    ResponseEntity<?> printStatement(String accountNumber, String email);

}
