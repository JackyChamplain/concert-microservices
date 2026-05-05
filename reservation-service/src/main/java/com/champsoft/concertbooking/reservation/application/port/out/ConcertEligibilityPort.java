package com.champsoft.concertbooking.reservation.application.port.out;

public interface ConcertEligibilityPort {
    boolean concertExists(String concertId);
}