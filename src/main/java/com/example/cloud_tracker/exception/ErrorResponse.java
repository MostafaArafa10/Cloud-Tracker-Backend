package com.example.cloud_tracker.exception;

public class ErrorResponse {
    private final String message;
    public ErrorResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
