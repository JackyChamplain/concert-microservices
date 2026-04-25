package com.champsoft.concertbooking.concerts.domain.model;

public class Concert {
    public final ConcertId id;
    public final Name name;
    public final Venue venue;
    public final Type type;
    public final Price price;
    public final boolean isPremium;

    public Concert(ConcertId id, Name name, Venue venue, Type type, Price price, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.type = type;
        this.price = price;
        this.isPremium = isPremium;
    }
}