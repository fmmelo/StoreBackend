package com.myproject.myproject.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
