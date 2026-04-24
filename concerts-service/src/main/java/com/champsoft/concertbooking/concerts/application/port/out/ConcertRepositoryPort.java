package com.champsoft.concertbooking.concerts.application.port.out;

import com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity;
import java.util.List;
import java.util.Optional;

public interface ConcertRepositoryPort {
    List<ConcertJpaEntity> findAll();
    Optional<ConcertJpaEntity> findById(String id);
    ConcertJpaEntity save(ConcertJpaEntity entity);
    void deleteById(String id);
}