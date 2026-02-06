package com.bank.frauddetection.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sender user ID (for transfer)
    private Long fromUserId;

    // Receiver user ID (for transfer)
    private Long toUserId;

    // Transaction amount
    private double amount;

    // DEPOSIT, WITHDRAW, TRANSFER
    private String type;

    // Timestamp of transaction
    private LocalDateTime timestamp;
}
