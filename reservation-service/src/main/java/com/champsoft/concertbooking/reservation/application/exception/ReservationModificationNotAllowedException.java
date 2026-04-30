package com.champsoft.concertbooking.reservation.application.exception;

public class ReservationModificationNotAllowedException extends RuntimeException {
    public ReservationModificationNotAllowedException(String message) {
        super(message);
    }
}