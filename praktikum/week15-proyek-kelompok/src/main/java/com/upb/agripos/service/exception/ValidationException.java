package com.upb.agripos.service.exception;

/**
 * Custom exception untuk validasi input/business logic
 * Digunakan untuk error handling di layer service
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
