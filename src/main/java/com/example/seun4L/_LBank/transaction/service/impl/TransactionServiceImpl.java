package com.example.seun4L._LBank.transaction.service.impl;

import com.example.seun4L._LBank.transaction.dto.TransactionResponseDto;
import com.example.seun4L._LBank.transaction.model.Transaction;
import com.example.seun4L._LBank.transaction.repository.TransactionRepository;
import com.example.seun4L._LBank.transaction.service.TransactionService;
import com.example.seun4L._LBank.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    @Override
    public TransactionResponseDto createTransaction(User user, BigDecimal amount, String transactionType, String userDescription) {
        Transaction transaction = new Transaction();
        String getTransactionType = transactionType.equals("debit") ? "debit" : "credit";
        String description = transactionType.equals("debit") ?
                "TRN/DBT/" + user.getUserId() + "/"
                        + user.getAccountNumber() + "/" + amount :
                "TRN/CRT/" + user.getUserId() + "/"
                        + user.getAccountNumber() + "/" + amount + "/" + userDescription;

        transaction.setDescription(description);
        transaction.setAmount(String.valueOf(amount));
        transaction.setAccountNumber(user.getAccountNumber());
        transaction.setAccountName(user.getAccountName());
        transaction.setTransactionType(getTransactionType);
        transaction.setAccountBalance(String.valueOf(user.getAccountBalance()));
        transaction.setUser(user);
        transaction.setUserDescription(userDescription);
        transaction.setStatus("Success");
        transaction.setReferenceNumber(generateReferenceNumber());
        transactionRepository.save(transaction);
        return getTransactionResponseDto(transaction);
    }



    private static TransactionResponseDto getTransactionResponseDto(Transaction transaction) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setTransactionId(transaction.getTransactionId());
        transactionResponseDto.setDescription(transaction.getDescription());
        transactionResponseDto.setAmount(transaction.getAmount());
        transactionResponseDto.setUserDescription(transaction.getUserDescription());
        transactionResponseDto.setAccountNumber(transaction.getAccountNumber());
        transactionResponseDto.setAccountName(transaction.getAccountName());
        transactionResponseDto.setAccountBalance(transaction.getAccountBalance());
        transactionResponseDto.setTransactionType(transaction.getTransactionType());
        transactionResponseDto.setStatus(transaction.getStatus());
        transactionResponseDto.setReferenceNumber(transaction.getReferenceNumber());
        transactionResponseDto.setCreatedAt(transaction.getCreatedAt());
        return transactionResponseDto;
    }
    @Override
    public List<TransactionResponseDto> getTransactionResponses(List<Transaction> transactions){
        List<TransactionResponseDto> transactionResponseDtos = new LinkedList<>();
        for (var transaction: transactions){
            transactionResponseDtos.add(getTransactionResponseDto(transaction));
        }
        return transactionResponseDtos;
    }
    private String generateReferenceNumber() {
        int remainingDigits = (int) Math.ceil(Math.random() * 10_000);
        String initialDigits = String.valueOf(new Date().getTime());

        String referenceNumber = initialDigits + "-" + remainingDigits;

        return referenceNumber;
    }
    @Override
    public TransactionResponseDto createTransferTransaction(User user, String receiver, BigDecimal amount, String transactionType, String userDescription) {
        Transaction transaction = new Transaction();
        String description = "TRN/TRF/" + amount + "/FRM" + "/"
                + user.getAccountNumber() + "/TO"+ "/" + receiver +
                "/" + userDescription;
        transaction.setDescription(description);
        transaction.setAmount(String.valueOf(amount));
        transaction.setAccountNumber(user.getAccountNumber());
        transaction.setAccountName(user.getAccountName());
        transaction.setTransactionType(transactionType);
        transaction.setAccountBalance(String.valueOf(user.getAccountBalance()));
        transaction.setUser(user);
        transaction.setStatus("success");
        transaction.setUserDescription(userDescription);
        transaction.setReferenceNumber(generateReferenceNumber());
        transactionRepository.save(transaction);

        return getTransactionResponseDto(transaction);
    }

}
