package com.bank.frauddetection.service;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.LoginResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest);
}
