package com.champsoft.concertBooking.modules.concert.domain.model;

public record Venue(String value) {
    public Venue {
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Venue cannot be empty");
        }
    }
}