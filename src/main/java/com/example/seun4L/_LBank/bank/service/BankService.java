package com.example.seun4L._LBank.bank.service;

import com.example.seun4L._LBank.bank.dto.BankRegisterDto;
import com.example.seun4L._LBank.bank.dto.BankTransactionDto;
import com.example.seun4L._LBank.bank.dto.BankUpdateDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface BankService {
    ResponseEntity<?> registerBank(BankRegisterDto bankRegisterDto);
    ResponseEntity<?> updateBankProfile(BankUpdateDto bankUpdateDto);
    String debitBank(String accountNumber, String bankCode, BigDecimal amount);
    String creditBank(String accountNumber, String bankCode, BigDecimal amount);
    ResponseEntity<?> getBankByAccountNumber(String accountNumber);
    ResponseEntity<?> getBankBalance(String accountNumber, String bankCode);
}
