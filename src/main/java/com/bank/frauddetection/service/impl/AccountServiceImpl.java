package com.bank.frauddetection.service.impl;

import com.bank.frauddetection.entity.Account;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.AccountRepository;
import com.bank.frauddetection.repository.UserRepository;
import com.bank.frauddetection.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public double getBalance(Long userId) {

        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ❗ Safety guard: balance should NEVER be negative
        if (account.getBalance() < 0) {
            account.setBalance(0);
            accountRepository.save(account);
        }

        return account.getBalance();
    }

    @Override
    public String deposit(Long userId, double amount) {

        // ❌ Reject invalid deposit
        if (amount <= 0) {
            return "Deposit amount must be greater than 0";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ❌ Blocked users cannot deposit
        if ("BLOCKED".equals(user.getStatus())) {
            return "Account is blocked";
        }

        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        return "Deposit successful";
    }

    @Override
    public Account getAccount(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
