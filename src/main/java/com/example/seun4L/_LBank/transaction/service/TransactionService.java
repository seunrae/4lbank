package com.example.seun4L._LBank.transaction.service;

import com.example.seun4L._LBank.transaction.dto.TransactionResponseDto;
import com.example.seun4L._LBank.transaction.model.Transaction;
import com.example.seun4L._LBank.user.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionResponseDto createTransaction(User user, BigDecimal amount, String transactionType, String userDescription);
    TransactionResponseDto createTransferTransaction(User user, String receiver, BigDecimal amount, String transactionType, String userDescription);

    List<TransactionResponseDto> getTransactionResponses(List<Transaction> transactions);
}
