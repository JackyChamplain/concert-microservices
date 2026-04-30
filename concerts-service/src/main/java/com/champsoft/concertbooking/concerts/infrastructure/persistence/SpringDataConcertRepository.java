package com.champsoft.concertbooking.concerts.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataConcertRepository extends JpaRepository<ConcertJpaEntity, String> {
}