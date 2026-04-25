package com.champsoft.concertbooking.showtimes.application.service;

import com.champsoft.concertbooking.showtimes.application.port.out.ShowtimeRepositoryPort;
import com.champsoft.concertbooking.showtimes.domain.exception.InvalidShowtimeException;
import com.champsoft.concertbooking.showtimes.domain.exception.ShowtimeNotFoundException;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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
                .orElseThrow(() -> new ShowtimeNotFoundException("Showtime not found with id: " + id));
    }

    public ShowtimeJpaEntity create(ShowtimeJpaEntity entity) {
        validateShowtime(entity);
        return port.save(entity);
    }

    public ShowtimeJpaEntity update(String id, ShowtimeJpaEntity entity) {
        return port.findById(id)
                .map(existing -> {
                    validateShowtime(entity);
                    entity.id = id;
                    return port.save(entity);
                })
                .orElseThrow(() -> new ShowtimeNotFoundException("Cannot update. Showtime not found with id: " + id));
    }

    public void delete(String id) {
        if (!port.findById(id).isPresent()) {
            throw new ShowtimeNotFoundException("Cannot delete. Showtime not found with id: " + id);
        }
        port.deleteById(id);
    }

    private void validateShowtime(ShowtimeJpaEntity entity) {
        if (entity.date == null) {
            throw new InvalidShowtimeException("Showtime date cannot be null.");
        }

        if (entity.time == null) {
            throw new InvalidShowtimeException("Showtime time cannot be null.");
        }

        LocalDateTime showDateTime = LocalDateTime.of(entity.date, entity.time);

        if (showDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidShowtimeException("Showtime cannot be in the past. Attempted: " + showDateTime);
        }
    }
}