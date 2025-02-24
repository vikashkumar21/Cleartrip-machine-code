package com.example.demo.exception;

public class CentreDoesNotExistException extends RuntimeException {

    public CentreDoesNotExistException(final String message) {
        super(message);
    }
}
