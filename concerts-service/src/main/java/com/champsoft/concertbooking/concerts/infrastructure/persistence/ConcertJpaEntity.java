package com.champsoft.concertbooking.concerts.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "concerts")
public class ConcertJpaEntity {

    @Id
    private String id;
    private String name;
    private String artist;
    private String venue;
    private String type;
    private double price;
    private boolean isPremium;

    public ConcertJpaEntity() {}

    public ConcertJpaEntity(String id,
                            String name,
                            String artist,
                            String venue,
                            String type,
                            double price,
                            boolean isPremium) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.venue = venue;
        this.type = type;
        this.price = price;
        this.isPremium = isPremium;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getVenue() { return venue; }
    public String getType() { return type; }
    public double getPrice() { return price; }

    public boolean isPremium() { return isPremium; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setVenue(String venue) { this.venue = venue; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setPremium(boolean premium) { isPremium = premium; }
}