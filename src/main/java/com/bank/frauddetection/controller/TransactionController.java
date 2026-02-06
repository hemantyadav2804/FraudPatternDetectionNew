package com.bank.frauddetection.controller;

import com.bank.frauddetection.dto.TransactionRequestDTO;
import com.bank.frauddetection.dto.TransactionResponseDTO;
import com.bank.frauddetection.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public TransactionResponseDTO transfer(
            @RequestBody TransactionRequestDTO request) {

        return transactionService.transferMoney(request);
    }
}
