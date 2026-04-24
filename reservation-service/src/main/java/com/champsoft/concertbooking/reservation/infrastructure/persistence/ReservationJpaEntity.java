package com.champsoft.concertbooking.reservation.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class ReservationJpaEntity {

    @Id
    public String id;

    @Column(name = "customer_id", nullable = false)
    public String customerId;

    @Column(name = "concert_id", nullable = false)
    public String concertId;

    @Column(name = "showtime_id", nullable = false)
    public String showtimeId;

    @Column(nullable = false)
    public String status;

    public ReservationJpaEntity() {}

    public ReservationJpaEntity(String id, String customerId, String concertId, String showtimeId, String status) {
        this.id = id;
        this.customerId = customerId;
        this.concertId = concertId;
        this.showtimeId = showtimeId;
        this.status = status;
    }
}