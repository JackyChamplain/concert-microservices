package com.champsoft.concertBooking.modules.showtime.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShowtimeRepository extends JpaRepository<ShowtimeJpaEntity, String> {
}