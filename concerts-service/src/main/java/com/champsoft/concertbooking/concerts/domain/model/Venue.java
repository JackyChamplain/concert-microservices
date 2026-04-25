package com.champsoft.concertbooking.concerts.domain.model;

public record Venue(String value) {
    public Venue {
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Venue cannot be empty");
        }
    }
}