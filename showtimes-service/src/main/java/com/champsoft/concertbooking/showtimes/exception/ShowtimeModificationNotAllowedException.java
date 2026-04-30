package com.champsoft.concertbooking.showtimes.exception;

public class ShowtimeModificationNotAllowedException extends RuntimeException {
    public ShowtimeModificationNotAllowedException(String message) {
        super(message);
    }
}