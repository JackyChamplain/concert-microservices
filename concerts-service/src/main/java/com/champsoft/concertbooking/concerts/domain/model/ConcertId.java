package com.champsoft.concertbooking.concerts.domain.model;

public record ConcertId(String value) {
    public ConcertId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Concert ID cannot be empty");
        }
    }
}