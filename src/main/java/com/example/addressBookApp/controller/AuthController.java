package com.example.addressBookApp.controller;

import com.example.addressBookApp.dto.AuthRequestDTO;
import com.example.addressBookApp.dto.AuthResponseDTO;
import com.example.addressBookApp.dto.ForgotPasswordRequest;
import com.example.addressBookApp.dto.ResetPasswordRequest;
import com.example.addressBookApp.model.User;
import com.example.addressBookApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Password has been reset successfully");
    }
    //To test your Forgot & Reset Password feature in Postman, follow these steps:
    //✅ 1. Forgot Password API (Generate Reset Token & Send Email)
    //📌 Endpoint:
    //bash
    //POST /api/auth/forgot-password
    //📌 Request Body (JSON):
    //json
    //{
    //    "email": "user@example.com"
    //}
    //📌 Expected Response (If Email Exists):
    //json
    //{
    //    "message": "Password reset email sent successfully. Check your inbox."
    //}
    //📌 Possible Error Responses:
    //
    //"User with this email does not exist."
    //"Failed to send email. Please try again."
    //🛠 What Happens Internally?
    //Spring Boot generates a reset token.
    //Saves the token in the database.
    //Sends an email with a reset link to the user.
    //✅ 2. Check Your Email for the Reset Link
    //If using Gmail SMTP, the email might look like:
    //perl
    //Subject: Reset Your Password
    //Click the link below to reset your password:
    //http://localhost:8080/api/auth/reset-password?token=XYZ123
    //⚠️ Copy the token from the email, as you'll need it in Step 3.
    //
    //✅ 3. Reset Password API (Verify Token & Update Password)
    //📌 Endpoint:
    //pgsql
    //POST /api/auth/reset-password
    //📌 Request Body (JSON):
    //json
    //{
    //    "token": "XYZ123",
    //    "newPassword": "NewStrongPassword123"
    //}
    //📌 Expected Response (If Successful)
    //json
    //{
    //    "message": "Password has been successfully reset. You can now login with your new password."
    //}
    //📌 Possible Error Responses:
    //
    //"Invalid or expired token."
    //"Password must be strong (min 8 chars, mix of letters & numbers)."
    //🛠 What Happens Internally?
    //
    //Checks if the token is valid.
    //Finds the user & updates the password (after encrypting it).
    //Deletes the token (so it can’t be reused).
    //✅ 4. Login With New Password
    //📌 Endpoint:
    //POST /api/auth/login
    //📌 Request Body (JSON):
    //
    //json
    //{
    //    "email": "user@example.com",
    //    "password": "NewStrongPassword123"
    //}
    //📌 Expected Response:
    //json
    //{
    //    "message": "Login successful",
    //    "token": "JWT-TOKEN"
    //}
}
