package com.bank.frauddetection.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bank.frauddetection.entity.AuditLog;
import com.bank.frauddetection.repository.AuditLogRepository;
import com.bank.frauddetection.service.AuditService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void logAction(String action, String performedBy) {

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setPerformedBy(performedBy);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }
}
