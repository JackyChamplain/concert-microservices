package com.champsoft.concertBooking.modules.reservation.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataReservationRepository extends JpaRepository<ReservationJpaEntity, String> {
}