package com.example.addressBookApp.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
