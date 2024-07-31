package com.example.seun4L._LBank.bank.repository;

import com.example.seun4L._LBank.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByBankCode(String bankCode);
    Optional<Bank> findByAccountNumber(String accountNumber);
    Optional<Bank> findByAccountNumberAndBankCode(String accountNumber, String bankCode);
}
