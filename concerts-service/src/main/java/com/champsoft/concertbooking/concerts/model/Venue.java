package com.champsoft.concertbooking.concerts.model;

import com.champsoft.concertbooking.concerts.domain.exception.InvalidVenueNameException;

public record Venue(String value) {
    public Venue {
        if (value == null || value.isBlank()) {
            throw new InvalidVenueNameException("Venue cannot be empty");
        }
    }
}