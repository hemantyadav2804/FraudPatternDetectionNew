package com.bank.frauddetection.fraud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bank.frauddetection.entity.LoginLog;
import com.bank.frauddetection.entity.User;
import com.bank.frauddetection.repository.LoginLogRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MultipleLoginFailureRule implements FraudRule {

    private final LoginLogRepository loginLogRepository;

    @Override
    public int apply(User user) {

        List<LoginLog> logs = loginLogRepository.findAll();

//        long failedAttempts = logs.stream()
//                // SAFE: user.getId() is non-null, equals(null) = false
//                .filter(log -> user.getId().equals(log.getUserId()))
//                .filter(log -> !log.isSuccess())
//                .count();
        long failedAttempts = logs.stream()
                .filter(log -> user.getId().equals(log.getUserId()))
                .filter(log -> !log.isSuccess())
                .filter(log -> log.getLoginTime()
                        .isAfter(LocalDateTime.now().minusMinutes(10)))
                .count();


        if (failedAttempts >= 3) {
            return 30; // risk points
        }
        return 0;
    }
}
