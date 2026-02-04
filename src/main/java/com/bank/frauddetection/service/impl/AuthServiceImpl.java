package com.bank.frauddetection.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.frauddetection.dto.LoginRequestDTO;
import com.bank.frauddetection.dto.LoginResponseDTO;
import com.bank.frauddetection.entity.LoginLog;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.LoginLogRepository;
import com.bank.frauddetection.repository.UserRepository;
import com.bank.frauddetection.service.AuthService;
import com.bank.frauddetection.service.FraudDetectionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final FraudDetectionService fraudDetectionService;
    
    @Override
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) {

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        // ❌ User not found → do NOT log (no userId)
        if (userOpt.isEmpty()) {
            return new LoginResponseDTO("Invalid username or password", null, null);
        }

        User user = userOpt.get();

        // ❌ Password mismatch → log failure
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            LoginLog log = new LoginLog();
            log.setUserId(user.getId());
            log.setIpAddress(httpRequest.getRemoteAddr());
            log.setLoginTime(LocalDateTime.now());
            log.setSuccess(false);

            loginLogRepository.save(log);

            // 🔥 Trigger fraud detection after failed login
            fraudDetectionService.detectFraud(user);

            return new LoginResponseDTO("Invalid username or password", null, null);
        }


        // 🚫 Blocked user
        if ("BLOCKED".equals(user.getStatus())) {
            return new LoginResponseDTO("User is blocked", null, null);
        }

        // ✅ Successful login → log success
        LoginLog log = new LoginLog();
        log.setUserId(user.getId());
        log.setIpAddress(httpRequest.getRemoteAddr());
        log.setLoginTime(LocalDateTime.now());
        log.setSuccess(true);

        loginLogRepository.save(log);

        return new LoginResponseDTO(
                "Login successful",
                user.getId(),
                user.getRole()
        );
    }


//    @Override
//    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) {
//
//        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
//
//        if (userOpt.isEmpty()) {
//            return new LoginResponseDTO("Invalid username or password", null, null);
//        }
//
//        User user = userOpt.get();
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return new LoginResponseDTO("Invalid username or password", null, null);
//        }
//
//        if ("BLOCKED".equals(user.getStatus())) {
//            return new LoginResponseDTO("User is blocked", null, null);
//        }
//
//        return new LoginResponseDTO(
//                "Login successful",
//                user.getId(),
//                user.getRole()
//        );
//    }
}
