package com.champsoft.concertbooking.showtimes.port.out;

import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface ShowtimeRepositoryPort {

    List<ShowtimeJpaEntity> findAll();

    Optional<ShowtimeJpaEntity> findById(String id);

    ShowtimeJpaEntity save(ShowtimeJpaEntity entity);

    void deleteById(String id);
}
