package com.champsoft.concertbooking.showtimes.exception;

public class ShowtimeAlreadyExistsException extends RuntimeException {
    public ShowtimeAlreadyExistsException(String message) {
        super(message);
    }
}