//UC10
package com.example.addressBookApp.service;

import com.example.addressBookApp.dto.AuthRequestDTO;
import com.example.addressBookApp.dto.AuthResponseDTO;
import com.example.addressBookApp.dto.ForgotPasswordRequest;
import com.example.addressBookApp.dto.ResetPasswordRequest;
import com.example.addressBookApp.exception.ResourceNotFoundException;
import com.example.addressBookApp.interfaces.IUserService;
import com.example.addressBookApp.model.User;
import com.example.addressBookApp.repository.UserRepository;
import com.example.addressBookApp.config.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use. Please use a different email.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully. Please login to continue.";
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO("Login successful", token);
    }

    // 🔹 Forgot Password - Generate Reset Token & Send Email
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Password Reset Request",
                "Click the link to reset your password: " + resetUrl);
    }

    // 🔹 Reset Password - Verify Token & Update Password
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);
    }
}



//UC6
//package com.example.addressBookApp.service;
//
//import com.example.addressBookApp.dto.AuthRequestDTO;
//import com.example.addressBookApp.dto.AuthResponseDTO;
//import com.example.addressBookApp.dto.ForgotPasswordRequest;
//import com.example.addressBookApp.dto.ResetPasswordRequest;
//import com.example.addressBookApp.interfaces.IUserService;
//import com.example.addressBookApp.model.User;
//import com.example.addressBookApp.repository.UserRepository;
//import com.example.addressBookApp.config.JwtUtil;
//import com.example.addressBookApp.service.EmailService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class UserService implements IUserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private EmailService emailService;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Override
//    public String registerUser(User user) {
//        // Check if user already exists
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            return "Email already in use. Please use a different email.";
//        }
//
//        // Encode the password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "User registered successfully. Please login to continue.";
//    }
//
//    @Override
//    public AuthResponseDTO login(AuthRequestDTO request) {
//        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//                String token = jwtUtil.generateToken(user.getEmail());
//                return new AuthResponseDTO("Login successful", token);
//            }
//        }
//        return new AuthResponseDTO("Invalid email or password", null);
//    }
//
//
//
//    // 🔹 Forgot Password - Generate Reset Token & Send Email
//    public void forgotPassword(ForgotPasswordRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Generate a reset token
//        String token = UUID.randomUUID().toString();
//        user.setResetToken(token);
//        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15)); // Token expires in 15 min
//        userRepository.save(user);
//
//        // Send password reset email
//        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
//        emailService.sendEmail(user.getEmail(), "Password Reset Request",
//                "Click the link to reset your password: " + resetUrl);
//    }
//
//    // 🔹 Reset Password - Verify Token & Update Password
//    public void resetPassword(ResetPasswordRequest request) {
//        User user = userRepository.findByResetToken(request.getToken())
//                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));
//
//        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Token has expired");
//        }
//
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        user.setResetToken(null);
//        user.setTokenExpiry(null);
//        userRepository.save(user);
//    }
//}


//package com.example.addressBookApp.service;
//
//import com.example.addressBookApp.dto.AuthRequestDTO;
//import com.example.addressBookApp.dto.AuthResponseDTO;
//import com.example.addressBookApp.interfaces.IUserService;
//import com.example.addressBookApp.model.User;
//import com.example.addressBookApp.repository.UserRepository;
//import com.example.addressBookApp.config.JwtUtil;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService implements IUserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Override
//    public String registerUser(User user) {
//        // Check if user already exists
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            return "Email already in use. Please use a different email.";
//        }
//
//        // Encode the password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "User registered successfully. Please login to continue.";
//    }
//
//    @Override
//    public AuthResponseDTO login(AuthRequestDTO request) {
//        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//                String token = jwtUtil.generateToken(user.getEmail());
//                return new AuthResponseDTO("Login successful", token);
//            }
//        }
//        return new AuthResponseDTO("Invalid email or password", null);
//    }
//}
