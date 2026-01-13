package com.upb.agripos;

public class EmptyStockException extends Exception {

    public EmptyStockException(String message) {
        super(message);
    }
}