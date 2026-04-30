package com.champsoft.concertbooking.concerts.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConcertJpaEntity {

    @Id
    public String id;

    public String name;
    public String venue;
}