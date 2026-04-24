package com.champsoft.concertBooking.modules.concert.domain.model;

public record Name(String value) {
    public Name {
        if (value == null || value.length() < 3) {
            throw new RuntimeException("Invalid Concert Name: Must be at least 3 characters");
        }
    }
}