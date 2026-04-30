package com.champsoft.concertbooking.showtimes.service;

import com.champsoft.concertbooking.showtimes.exception.ShowtimeAlreadyExistsException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeModificationNotAllowedException;
import com.champsoft.concertbooking.showtimes.port.out.ShowtimeRepositoryPort;
import com.champsoft.concertbooking.showtimes.domain.exception.InvalidShowtimeException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeNotFoundException;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .orElseThrow(() ->
                        new ShowtimeNotFoundException("Showtime not found with id: " + id));
    }

    public ShowtimeJpaEntity create(ShowtimeJpaEntity entity) {

        if (entity.getId() != null && port.findById(entity.getId()).isPresent()) {
            throw new ShowtimeAlreadyExistsException(
                    "Showtime with ID " + entity.getId() + " already exists.");
        }

        validateShowtime(entity);
        return port.save(entity);
    }

    public ShowtimeJpaEntity update(String id, ShowtimeJpaEntity entity) {

        ShowtimeJpaEntity existing = port.findById(id)
                .orElseThrow(() ->
                        new ShowtimeNotFoundException("Cannot update. Showtime not found with id: " + id));

        if (isInPast(existing)) {
            throw new ShowtimeModificationNotAllowedException(
                    "Showtime " + id + " has already passed and cannot be modified.");
        }

        validateShowtime(entity);
        entity.setId(id);

        return port.save(entity);
    }

    public void delete(String id) {

        ShowtimeJpaEntity existing = port.findById(id)
                .orElseThrow(() ->
                        new ShowtimeNotFoundException("Cannot delete. Showtime not found with id: " + id));

        if (isInPast(existing)) {
            throw new ShowtimeModificationNotAllowedException(
                    "Showtime " + id + " has already passed and cannot be deleted.");
        }

        port.deleteById(id);
    }

    private void validateShowtime(ShowtimeJpaEntity entity) {

        if (entity.getDate() == null) {
            throw new InvalidShowtimeException("Showtime date cannot be null.");
        }

        if (entity.getTime() == null) {
            throw new InvalidShowtimeException("Showtime time cannot be null.");
        }

        LocalDateTime showDateTime =
                LocalDateTime.of(entity.getDate(), entity.getTime());

        if (showDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidShowtimeException(
                    "Showtime cannot be in the past. Attempted: " + showDateTime);
        }
    }

    private boolean isInPast(ShowtimeJpaEntity entity) {

        if (entity.getDate() == null || entity.getTime() == null) {
            return false;
        }

        return LocalDateTime.of(entity.getDate(), entity.getTime())
                .isBefore(LocalDateTime.now());
    }
}