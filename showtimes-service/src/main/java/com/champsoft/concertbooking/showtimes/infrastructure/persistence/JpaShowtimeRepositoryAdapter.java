package com.champsoft.concertBooking.modules.showtime.infrastructure.persistence;

import com.champsoft.concertBooking.modules.showtime.application.port.out.ShowtimeRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaShowtimeRepositoryAdapter implements ShowtimeRepositoryPort {

    private final SpringDataShowtimeRepository springRepo;

    public JpaShowtimeRepositoryAdapter(SpringDataShowtimeRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public List<ShowtimeJpaEntity> findAll() {
        return springRepo.findAll();
    }

    @Override
    public ShowtimeJpaEntity save(ShowtimeJpaEntity entity) {
        return springRepo.save(entity);
    }

    @Override
    public Optional<ShowtimeJpaEntity> findById(String id) {
        return springRepo.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springRepo.deleteById(id);
    }
}