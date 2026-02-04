package com.bank.frauddetection.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.LoginResponseDTO;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.UserRepository;
import com.bank.frauddetection.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) {

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            return new LoginResponseDTO("Invalid username or password", null, null);
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO("Invalid username or password", null, null);
        }

        if ("BLOCKED".equals(user.getStatus())) {
            return new LoginResponseDTO("User is blocked", null, null);
        }

        return new LoginResponseDTO(
                "Login successful",
                user.getId(),
                user.getRole()
        );
    }
}
