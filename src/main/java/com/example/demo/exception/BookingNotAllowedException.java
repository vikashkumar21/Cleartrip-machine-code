package com.example.demo.exception;

public class BookingNotAllowedException extends RuntimeException {
    
    public BookingNotAllowedException(final String message) {
        super(message);
    }
}
