package com.example.addressBookApp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.addressBookApp.dto.AuthRequestDTO;
import com.example.addressBookApp.dto.AuthResponseDTO;
import com.example.addressBookApp.exception.ResourceNotFoundException;
import com.example.addressBookApp.model.User;
import com.example.addressBookApp.repository.UserRepository;
import com.example.addressBookApp.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword(passwordEncoder.encode("password"));
    }

    @Test
    void testLogin_Success() {
        AuthRequestDTO request = new AuthRequestDTO("test@example.com", "password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken("test@example.com")).thenReturn("mockedToken");

        AuthResponseDTO response = userService.login(request);

        assertEquals("Login successful", response.getMessage());
        assertNotNull(response.getToken());
    }

    @Test
    void testLogin_InvalidPassword() {
        AuthRequestDTO request = new AuthRequestDTO("test@example.com", "wrongpassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        Exception exception = assertThrows(BadCredentialsException.class, () -> userService.login(request));
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        AuthRequestDTO request = new AuthRequestDTO("notfound@example.com", "password");

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.login(request));
        assertEquals("User not found with email: notfound@example.com", exception.getMessage());
    }
}
