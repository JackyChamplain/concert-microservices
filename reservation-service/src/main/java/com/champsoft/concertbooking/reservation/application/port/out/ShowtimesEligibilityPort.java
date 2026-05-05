package com.champsoft.concertbooking.reservation.application.port.out;

public interface ShowtimesEligibilityPort {
    boolean showtimeExists(String showtimeId);
}