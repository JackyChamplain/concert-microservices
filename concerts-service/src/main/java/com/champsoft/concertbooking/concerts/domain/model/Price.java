package com.champsoft.concertbooking.concerts.domain.model;

public record Price(double value) {
    public Price {
        if (value < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}