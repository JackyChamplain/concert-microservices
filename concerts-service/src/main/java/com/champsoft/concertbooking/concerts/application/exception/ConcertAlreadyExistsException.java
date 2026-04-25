package com.champsoft.concertbooking.concerts.application.exception;

public class ConcertAlreadyExistsException extends RuntimeException {
    public ConcertAlreadyExistsException(String message) {
        super(message);
    }
}