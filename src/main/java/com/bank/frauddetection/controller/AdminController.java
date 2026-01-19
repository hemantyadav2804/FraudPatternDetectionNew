package com.bank.frauddetection.controller;

import org.springframework.web.bind.annotation.*;

import com.bank.frauddetection.audit.AuditLogger;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final AuditLogger auditLogger;

    @PostMapping("/block/{userId}")
    public String blockUser(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus("BLOCKED");
        userRepository.save(user);

        auditLogger.log(
                "Blocked user with ID: " + userId,
                "ADMIN"
        );

        return "User blocked successfully";
    }
}
