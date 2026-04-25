package com.champsoft.concertbooking.concerts.domain.exception;

public class InvalidVenueNameException extends RuntimeException {
    public InvalidVenueNameException(String message) {
        super(message);
    }
}