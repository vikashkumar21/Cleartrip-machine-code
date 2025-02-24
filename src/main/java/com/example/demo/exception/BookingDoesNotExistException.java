package com.example.demo.exception;

public class BookingDoesNotExistException extends RuntimeException {

    public BookingDoesNotExistException(final String message) {
        super(message);
    }
}
