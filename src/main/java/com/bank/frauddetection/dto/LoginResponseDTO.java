package com.bank.frauddetection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private Long userId;
    private String role;
    private String username;
}

