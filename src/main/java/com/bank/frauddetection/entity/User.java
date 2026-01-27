package com.bank.frauddetection.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;        // USER, ADMIN

    private String status;      // ACTIVE, BLOCKED

    private int riskScore;

    // =========================
    // OTP FOR FORGOT PASSWORD
    // =========================
    private String otp;

    private LocalDateTime otpExpiry;
}
