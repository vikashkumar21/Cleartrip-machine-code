package com.example.demo.exception;

public class ActivityNotSupportedException extends RuntimeException {

    public ActivityNotSupportedException(final String message) {
        super(message);
    }
}
