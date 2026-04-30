package com.champsoft.concertbooking.concerts.application.service;

import com.champsoft.concertbooking.concerts.application.exception.ConcertAlreadyExistsException;
import com.champsoft.concertbooking.concerts.application.exception.ConcertNotFoundException;
import com.champsoft.concertbooking.concerts.application.port.out.ConcertRepositoryPort;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidConcertNameException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidVenueNameException;
import com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConcertCrudService {
    private final ConcertRepositoryPort repositoryPort;

    public ConcertCrudService(ConcertRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<ConcertJpaEntity> getAll() {
        return repositoryPort.findAll();
    }

    public ConcertJpaEntity getById(String id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException("Concert not found with ID: " + id));
    }

    public ConcertJpaEntity create(ConcertJpaEntity entity) {
        if (repositoryPort.findById(entity.id).isPresent()) {
            throw new ConcertAlreadyExistsException("Concert with ID " + entity.id + " already exists.");
        }
        validateConcert(entity);
        return repositoryPort.save(entity);
    }

    public ConcertJpaEntity update(String id, ConcertJpaEntity entity) {
        return repositoryPort.findById(id)
                .map(existingConcert -> {
                    validateConcert(entity);
                    entity.id = id;
                    return repositoryPort.save(entity);
                })
                .orElseThrow(() -> new RuntimeException("Cannot update. Concert not found with id: " + id));
    }

    public void delete(String id) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new RuntimeException("Cannot delete. Concert not found.");
        }
        repositoryPort.deleteById(id);
    }

    private void validateConcert(ConcertJpaEntity entity) {
        if (entity.name == null || entity.name.isBlank()) {
            throw new InvalidConcertNameException("Concert name is required and cannot be empty.");
        }

        if (entity.venue == null || entity.venue.isBlank()) {
            throw new InvalidVenueNameException("Venue name is required and cannot be empty.");
        }
    }
}