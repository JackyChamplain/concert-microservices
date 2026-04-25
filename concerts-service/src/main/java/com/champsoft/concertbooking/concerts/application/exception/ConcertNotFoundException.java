package com.champsoft.concertbooking.concerts.application.exception;

public class ConcertNotFoundException extends RuntimeException {
    public ConcertNotFoundException(String message) {
        super(message);
    }
}