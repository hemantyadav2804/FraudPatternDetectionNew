package com.bank.frauddetection.audit;

import org.springframework.stereotype.Component;

import com.bank.frauddetection.service.AuditService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuditLogger {

    private final AuditService auditService;

    public void log(String action, String performedBy) {
        auditService.logAction(action, performedBy);
    }
}
