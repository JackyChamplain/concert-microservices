package com.champsoft.concertbooking.reservation.infrastructure.persistence;

import com.champsoft.concertbooking.reservation.application.port.out.ReservationRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final SpringDataReservationRepository springRepo;

    public JpaReservationRepositoryAdapter(SpringDataReservationRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public List<ReservationJpaEntity> findAll() {
        return springRepo.findAll();
    }

    @Override
    public Optional<ReservationJpaEntity> findById(String id) {
        return springRepo.findById(id);
    }

    @Override
    public ReservationJpaEntity save(ReservationJpaEntity entity) {
        return springRepo.save(entity);
    }

    @Override
    public void deleteById(String id) {
        springRepo.deleteById(id);
    }
}