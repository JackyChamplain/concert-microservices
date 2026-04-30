package com.champsoft.concertbooking.concerts.application.service.exception;

public class ConcertAlreadyExistsException extends RuntimeException {
    public ConcertAlreadyExistsException(String message) {
        super(message);
    }
}