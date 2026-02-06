package com.bank.frauddetection.controller;

import com.bank.frauddetection.dto.*;
import com.bank.frauddetection.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request,
            HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String username) {
        return authService.generateOtp(username);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String username,
            @RequestParam String otp,
            @RequestParam String newPassword) {
        return authService.resetPassword(username, otp, newPassword);
    }
}
