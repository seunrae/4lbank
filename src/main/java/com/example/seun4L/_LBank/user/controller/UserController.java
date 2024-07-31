package com.example.seun4L._LBank.user.controller;

import com.example.seun4L._LBank.user.dto.UserRequestDto;
import com.example.seun4L._LBank.user.dto.UserTransactionDto;
import com.example.seun4L._LBank.user.dto.UserTransferDto;
import com.example.seun4L._LBank.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestDto){
        return userService.updateUser(userRequestDto);
    }
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        return userService.deleteUser();
    }
    @GetMapping("/get-user")
    public ResponseEntity<?> getUserById() {
        return userService.getUserById();
    }
    @GetMapping("/get-user-by-accountNumber")
    public ResponseEntity<?> getUserByAccountNumber(@RequestParam(name = "accountNumber") String accountNumber) {
        return userService.getUserByAccountNumber(accountNumber);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/debit-user")
    public ResponseEntity<?> debitUserAccount(@RequestBody UserTransactionDto userTransactionDto) {
        return userService.debitUserAccount(userTransactionDto);
    }
    @PostMapping("/credit-user")
    public ResponseEntity<?> creditUserAccount(@RequestBody UserTransactionDto userTransactionDto) {
        return userService.creditUserAccount(userTransactionDto);
    }

    @PostMapping("/transfer-funds")
    public ResponseEntity<?> transferFunds(@RequestBody UserTransferDto userTransferDto) {
        return userService.transferFunds(userTransferDto);
    }
    @GetMapping("/get-balance")
    public ResponseEntity<?> getAccountBalance(@RequestParam(name = "accountNumber") String accountNumber) {
        return userService.getAccountBalance(accountNumber);
    }

    @GetMapping("/print-statement")
    public ResponseEntity<?> printStatement(@RequestParam(name = "accountNumber") String accountNumber,
                                            @RequestParam(name = "email") String email){
        return userService.printStatement(accountNumber, email);
    }
}
