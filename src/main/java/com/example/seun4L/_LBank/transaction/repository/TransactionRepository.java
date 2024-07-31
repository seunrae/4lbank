package com.example.seun4L._LBank.transaction.repository;

import com.example.seun4L._LBank.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByReferenceNumber(String referenceNumber);
}
