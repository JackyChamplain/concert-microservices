package com.champsoft.concertbooking.reservation.application.service;

import com.champsoft.concertbooking.reservation.api.dto.UpdateReservationRequest;
import com.champsoft.concertbooking.reservation.application.port.out.ReservationRepositoryPort;
import com.champsoft.concertbooking.reservation.infrastructure.persistence.ReservationJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationOrchestrator {
    private final ReservationRepositoryPort repositoryPort;

    public RegistrationOrchestrator(ReservationRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<ReservationJpaEntity> getAllReservations() {
        return repositoryPort.findAll();
    }

    public ReservationJpaEntity getById(String id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + id));
    }

    public ReservationJpaEntity register(ReservationJpaEntity reservation) {
        // Business logic check: Don't allow duplicate bookings for same customer/showtime
        boolean exists = repositoryPort.findAll().stream()
                .anyMatch(r -> r.customerId.equals(reservation.customerId)
                        && r.showtimeId.equals(reservation.showtimeId));

        if (exists) throw new RuntimeException("Customer is already booked for this showtime!");

        return repositoryPort.save(reservation);
    }

    public ReservationJpaEntity updateReservation(String id, UpdateReservationRequest request) {
        ReservationJpaEntity existing = getById(id);

        // Update fields (status is the most common update for a reservation)
        existing.status = request.status();

        return repositoryPort.save(existing);
    }

    public void deleteReservation(String id) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new RuntimeException("Cannot delete. Reservation not found.");
        }
        repositoryPort.deleteById(id);
    }
}