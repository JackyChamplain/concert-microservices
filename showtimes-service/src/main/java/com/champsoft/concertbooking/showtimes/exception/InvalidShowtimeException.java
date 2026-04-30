package com.champsoft.concertbooking.showtimes.exception;


public class InvalidShowtimeException extends RuntimeException {
    public InvalidShowtimeException(String message) {
        super(message);
    }
}