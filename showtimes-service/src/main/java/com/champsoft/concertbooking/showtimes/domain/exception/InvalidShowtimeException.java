package com.champsoft.concertbooking.showtimes.domain.exception;

public class InvalidShowtimeException extends RuntimeException {
    public InvalidShowtimeException(String message) {
        super(message);
    }
}