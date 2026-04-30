package com.champsoft.concertbooking.showtimes.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "showtimes")
public class ShowtimeJpaEntity {

    @Id
    private String id;
    private LocalDate date;
    private LocalTime time;
    private String concertId;

    public ShowtimeJpaEntity() {}

    public ShowtimeJpaEntity(String id,
                             LocalDate date,
                             LocalTime time,
                             String concertId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.concertId = concertId;
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getConcertId() { return concertId; }

    public void setId(String id) { this.id = id; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setConcertId(String concertId) { this.concertId = concertId; }
}