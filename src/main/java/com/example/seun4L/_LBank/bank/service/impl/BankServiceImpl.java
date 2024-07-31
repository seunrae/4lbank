package com.example.seun4L._LBank.bank.service.impl;

import com.example.seun4L._LBank.bank.dto.BankRegisterDto;
import com.example.seun4L._LBank.bank.dto.BankTransactionDto;
import com.example.seun4L._LBank.bank.dto.BankUpdateDto;
import com.example.seun4L._LBank.bank.model.Bank;
import com.example.seun4L._LBank.bank.repository.BankRepository;
import com.example.seun4L._LBank.bank.service.BankService;
import com.example.seun4L._LBank.exception.BankNotFoundException;
import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Override
    public ResponseEntity<?> registerBank(BankRegisterDto bankRegisterDto) {
        try {
            Bank bank = new Bank();
            bank.setBankCode("01");
            bank.setBankName(bankRegisterDto.getBankName());
            bank.setAddress(bankRegisterDto.getAddress());
            bank.setEmail(bankRegisterDto.getEmail());
            bank.setCountry(bankRegisterDto.getCountry());
            bank.setPhone(bankRegisterDto.getPhone());
            bank.setAccountBalance(bankRegisterDto.getAccountBalance());
            bank.setAccountNumber(bankRegisterDto.getAccountNumber());
            bank.setAccounttype(ACCOUNTTYPE.valueOf(ACCOUNTTYPE.CURRENT.toString().toUpperCase()));
            bankRepository.save(bank);
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateBankProfile(BankUpdateDto bankUpdateDto) {
        try {
            Bank bank = bankRepository.findByBankCode("01").orElseThrow(() -> new BankNotFoundException("Bank code does not exist"));
            bank.setBankName(bankUpdateDto.getBankName());
            bank.setEmail(bankUpdateDto.getEmail());
            bank.setAddress(bankUpdateDto.getAddress());
            bank.setPhone(bankUpdateDto.getPhone());
            bank.setCountry(bankUpdateDto.getCountry());

            bankRepository.save(bank);
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String debitBank(String accountNumber, String bankCode, BigDecimal amount) {
        try {
            Bank bank = bankRepository.findByAccountNumberAndBankCode(accountNumber, bankCode).orElseThrow(() -> new BankNotFoundException("Account number not found"));
            if (bank.getAccountBalance().compareTo(amount) >= 0) {
                bank.setAccountBalance(bank.getAccountBalance().subtract(amount));
                bankRepository.save(bank);
                return "bank debitted";
            }
            return "Insufficient funds";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    public String creditBank(String accountNumber, String bankCode, BigDecimal amount) {
        try {
            Bank bank = bankRepository.findByAccountNumberAndBankCode(accountNumber, bankCode).orElseThrow(() -> new BankNotFoundException("Account number not found"));
            bank.setAccountBalance(bank.getAccountBalance().add(amount));
            bankRepository.save(bank);
            return "Bank credited";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    public ResponseEntity<?> getBankByAccountNumber(String accountNumber) {
        try {
            Bank bank = bankRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BankNotFoundException("Account number not found"));
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getBankBalance(String accountNumber, String bankCode) {
        try {
            Bank bank = bankRepository.findByAccountNumberAndBankCode(accountNumber, bankCode).orElseThrow(() -> new BankNotFoundException("Account number not found"));
            Map<String, String> response = new HashMap<>();
            response.put("balance", bank.getAccountBalance().toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
