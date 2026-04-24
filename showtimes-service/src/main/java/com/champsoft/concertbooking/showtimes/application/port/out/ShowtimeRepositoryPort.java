package com.champsoft.concertBooking.modules.showtime.application.port.out;

import com.champsoft.concertBooking.modules.showtime.infrastructure.persistence.ShowtimeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface ShowtimeRepositoryPort {
    List<ShowtimeJpaEntity> findAll();

    ShowtimeJpaEntity save(ShowtimeJpaEntity entity);
    Optional<ShowtimeJpaEntity> findById(String id);
    void deleteById(String id);
}
