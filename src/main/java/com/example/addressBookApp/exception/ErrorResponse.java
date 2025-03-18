package com.example.addressBookApp.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String details;

    public ErrorResponse(int status, String message, LocalDateTime timestamp, String details) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}
