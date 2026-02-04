package com.bank.frauddetection.controller;

import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
@RequiredArgsConstructor
public class FraudController {

    private final UserRepository userRepository;

    // Get fraud risk score for user
    @GetMapping("/risk-score/{userId}")
    public int getRiskScore(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getRiskScore();
    }
}
