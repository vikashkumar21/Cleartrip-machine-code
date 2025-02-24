package com.example.demo.exception;

public class TimingMismatchException extends RuntimeException {
    
    public TimingMismatchException(final String message) {
        super(message);
    }
}
