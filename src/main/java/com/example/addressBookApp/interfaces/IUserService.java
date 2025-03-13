package com.example.addressBookApp.interfaces;

import com.example.addressBookApp.dto.AuthRequestDTO;
import com.example.addressBookApp.dto.AuthResponseDTO;
import com.example.addressBookApp.model.User;

public interface IUserService {
    String registerUser(User user); // Registration returns a success message
    AuthResponseDTO login(AuthRequestDTO request);
}
