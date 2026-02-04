package com.bank.frauddetection.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bank.frauddetection.dto.TransactionRequestDTO;
import com.bank.frauddetection.dto.TransactionResponseDTO;
import com.bank.frauddetection.entity.Account;
import com.bank.frauddetection.entity.Transaction;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.AccountRepository;
import com.bank.frauddetection.repository.TransactionRepository;
import com.bank.frauddetection.repository.UserRepository;
import com.bank.frauddetection.service.FraudDetectionService;
import com.bank.frauddetection.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final FraudDetectionService fraudDetectionService;

    @Override
    public TransactionResponseDTO transferMoney(TransactionRequestDTO request) {

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        User user = userRepository.findById(fromAccount.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🚫 BLOCKED USER CHECK (DAY 9)
        if ("BLOCKED".equals(user.getStatus())) {
            return new TransactionResponseDTO("FAILED", "User is blocked");
        }

        if (fromAccount.getBalance() < request.getAmount()) {
            return new TransactionResponseDTO("FAILED", "Insufficient balance");
        }

        // Debit sender
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());

        // Credit receiver
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount.getId());
        transaction.setToAccount(toAccount.getId());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        transactionRepository.save(transaction);

        // Fraud detection
        fraudDetectionService.detectFraud(user);

        return new TransactionResponseDTO("SUCCESS", "Transaction completed");
    }

}