package com.champsoft.concertbooking.reservation.port.out;

import com.champsoft.concertbooking.reservation.infrastructure.persistence.ReservationJpaEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {

    List<ReservationJpaEntity> findAll();

    Optional<ReservationJpaEntity> findById(String id);

    ReservationJpaEntity save(ReservationJpaEntity entity);

    void deleteById(String id);
}