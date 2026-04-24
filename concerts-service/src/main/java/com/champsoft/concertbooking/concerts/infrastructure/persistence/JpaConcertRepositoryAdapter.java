package com.champsoft.concertBooking.modules.concert.infrastructure.persistence;

import com.champsoft.concertBooking.modules.concert.application.port.out.ConcertRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JpaConcertRepositoryAdapter implements ConcertRepositoryPort {
    private final SpringDataConcertRepository springRepo;

    public JpaConcertRepositoryAdapter(SpringDataConcertRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public ConcertJpaEntity save(ConcertJpaEntity entity) {
        return springRepo.save(entity);
    }

    @Override
    public List<ConcertJpaEntity> findAll() {
        return springRepo.findAll();
    }

    @Override
    public Optional<ConcertJpaEntity> findById(String id) {
        return springRepo.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springRepo.deleteById(id);
    }
}