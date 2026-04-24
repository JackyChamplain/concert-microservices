package com.champsoft.concertBooking.modules.concert.application.port.out;

import com.champsoft.concertBooking.modules.concert.infrastructure.persistence.ConcertJpaEntity;
import java.util.List;
import java.util.Optional;

public interface ConcertRepositoryPort {
    List<ConcertJpaEntity> findAll();
    Optional<ConcertJpaEntity> findById(String id);
    ConcertJpaEntity save(ConcertJpaEntity entity);
    void deleteById(String id);
}