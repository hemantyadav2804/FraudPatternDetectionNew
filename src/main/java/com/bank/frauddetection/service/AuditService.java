package com.bank.frauddetection.service;

public interface AuditService {

    void logAction(String action, String performedBy);
}
