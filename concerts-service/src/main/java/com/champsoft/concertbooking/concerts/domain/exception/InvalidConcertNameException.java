package com.champsoft.concertbooking.concerts.domain.exception;

public class InvalidConcertNameException extends RuntimeException {
    public InvalidConcertNameException(String message) {
        super(message);
    }
}