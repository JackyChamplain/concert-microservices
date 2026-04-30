package com.champsoft.concertbooking.customers.exception;

public class CustomerEmailAlreadyInUseException extends RuntimeException {
    public CustomerEmailAlreadyInUseException(String message) {
        super(message);
    }
}