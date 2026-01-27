package com.bank.frauddetection.service;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.RegisterRequestDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

	String register(RegisterRequestDTO request);

    String login(LoginRequestDTO request, HttpServletRequest httpRequest);

    // =========================
    // OTP BASED FORGOT PASSWORD
    // =========================
    String generateOtp(String username);

    String resetPassword(String username, String otp, String newPassword);
}
