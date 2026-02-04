package com.bank.frauddetection.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {

    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
}