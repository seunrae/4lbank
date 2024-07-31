package com.example.seun4L._LBank.bank.controller;

import com.example.seun4L._LBank.bank.dto.BankRegisterDto;
import com.example.seun4L._LBank.bank.dto.BankUpdateDto;
import com.example.seun4L._LBank.bank.service.BankService;
import com.example.seun4L._LBank.user.dto.UserTransferDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/bank")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    @PostMapping("/register-bank")
    public ResponseEntity<?> registerBank(@RequestBody BankRegisterDto bankRegisterDto) {
        return bankService.registerBank(bankRegisterDto);
    }
    @PutMapping("/update-bank")
    public ResponseEntity<?> updateBankProfile(@RequestBody BankUpdateDto bankUpdateDto) {
        return bankService.updateBankProfile(bankUpdateDto);
    }
    @PostMapping("/debit-bank")
    public String debitBank(@RequestParam(name = "accountNumber") String accountNumber,
                            @RequestParam(name = "bankCode") String bankCode,
                            @RequestParam(name = "amount") BigDecimal amount) {
        return bankService.debitBank(accountNumber, bankCode, amount);
    }
    @PostMapping("/credit-bank")
    public String creditBank(@RequestParam(name = "accountNumber") String accountNumber,
                            @RequestParam(name = "bankCode") String bankCode,
                            @RequestParam(name = "amount") BigDecimal amount) {
        return bankService.creditBank(accountNumber, bankCode, amount);
    }
    @GetMapping("get-bank-by-accountNumber")
    public ResponseEntity<?> getBankByAccountNumber(@RequestParam(name = "accountNumber") String accountNumber){
        return bankService.getBankByAccountNumber(accountNumber);
    }
    @GetMapping("get-bank-balance")
    public ResponseEntity<?> getBankBalance(@RequestParam(name = "accountNumber") String accountNumber,@RequestParam(name = "bankCode") String bankCode){
        return bankService.getBankBalance(accountNumber, bankCode);
    }

}
