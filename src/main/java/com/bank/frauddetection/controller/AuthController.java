package com.bank.frauddetection.controller;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.LoginResponseDTO;
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

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request,
                                  HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }
}
