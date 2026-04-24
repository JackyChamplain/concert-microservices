package com.champsoft.concertbooking.concerts.application.service;

import com.champsoft.concertbooking.concerts.application.port.out.ConcertRepositoryPort;
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
                .orElseThrow(() -> new RuntimeException("Concert not found with id: " + id));
    }

    public ConcertJpaEntity create(ConcertJpaEntity entity) {
        return repositoryPort.save(entity);
    }

    public ConcertJpaEntity update(String id, ConcertJpaEntity entity) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new RuntimeException("Cannot update. Concert not found.");
        }
        entity.id = id;
        return repositoryPort.save(entity);
    }

    public void delete(String id) {
        repositoryPort.deleteById(id);
    }
}