package com.champsoft.concertbooking.showtimes.application.service;

import com.champsoft.concertbooking.showtimes.application.port.out.ShowtimeRepositoryPort;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShowtimeCrudService {
    private final ShowtimeRepositoryPort port;

    public ShowtimeCrudService(ShowtimeRepositoryPort port) {
        this.port = port;
    }

    public List<ShowtimeJpaEntity> getAll() {
        return port.findAll();
    }

    public ShowtimeJpaEntity getById(String id) {
        return port.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found: " + id));
    }

    public ShowtimeJpaEntity create(ShowtimeJpaEntity entity) {
        return port.save(entity);
    }

    public ShowtimeJpaEntity update(String id, ShowtimeJpaEntity entity) {
        if (port.findById(id).isEmpty()) {
            throw new RuntimeException("Cannot update. Showtime not found.");
        }
        entity.id = id;
        return port.save(entity);
    }

    public void delete(String id) {
        port.deleteById(id);
    }
}