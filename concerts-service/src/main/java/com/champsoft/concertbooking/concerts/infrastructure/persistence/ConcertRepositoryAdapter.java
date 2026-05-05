package com.champsoft.concertbooking.concerts.infrastructure.persistence;

import com.champsoft.concertbooking.concerts.application.port.out.ConcertRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConcertRepositoryAdapter implements ConcertRepositoryPort {

    private final SpringDataConcertRepository repository;

    public ConcertRepositoryAdapter(SpringDataConcertRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ConcertJpaEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<ConcertJpaEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public ConcertJpaEntity save(ConcertJpaEntity entity) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    // implement methods from ConcertRepositoryPort
}
