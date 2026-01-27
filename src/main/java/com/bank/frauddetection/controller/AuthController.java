package com.bank.frauddetection.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.RegisterRequestDTO;
import com.bank.frauddetection.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request,
                        HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }

    // =========================
    // REGISTER
    // =========================
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    // =========================
    // FORGOT PASSWORD - OTP
    // =========================
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String username) {
        return authService.generateOtp(username);
    }

    // =========================
    // RESET PASSWORD
    // =========================
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String otp,
                                @RequestParam String newPassword) {
        return authService.resetPassword(username, otp, newPassword);
    }
}
