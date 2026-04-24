package com.champsoft.concertBooking.modules.reservation.application.port.out;

import com.champsoft.concertBooking.modules.reservation.infrastructure.persistence.ReservationJpaEntity;
import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {
    List<ReservationJpaEntity> findAll();
    Optional<ReservationJpaEntity> findById(String id);
    ReservationJpaEntity save(ReservationJpaEntity entity);
    void deleteById(String id);
}