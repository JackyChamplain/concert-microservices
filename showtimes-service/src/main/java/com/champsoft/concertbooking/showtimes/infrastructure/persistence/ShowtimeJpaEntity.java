package com.champsoft.concertBooking.modules.showtime.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "showtimes")
public class ShowtimeJpaEntity {
    @Id
    public String id;

    public LocalDate date;
    public LocalTime time;

    @Column(name = "concert_id")
    public String concertId;

    public ShowtimeJpaEntity() {}

    public ShowtimeJpaEntity(String id, LocalDate date, LocalTime time, String concertId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.concertId = concertId;
    }
}