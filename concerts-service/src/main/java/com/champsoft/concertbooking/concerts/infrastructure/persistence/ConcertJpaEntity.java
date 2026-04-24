package com.champsoft.concertBooking.modules.concert.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "concerts")
public class ConcertJpaEntity {

    @Id
    public String id;

    public String name;
    public String venue;
    public String type;
    public double price;

    @Column(name = "is_premium")
    public boolean isPremium;

    public ConcertJpaEntity() {}

    public ConcertJpaEntity(String id, String name, String venue, String type, double price, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.type = type;
        this.price = price;
        this.isPremium = isPremium;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public String getVenue() { return venue; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isPremium() { return isPremium; }
}