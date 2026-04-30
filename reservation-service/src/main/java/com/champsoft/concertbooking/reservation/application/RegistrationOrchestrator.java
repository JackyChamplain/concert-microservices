package com.champsoft.concertbooking.reservation.application;

import com.champsoft.concertbooking.reservation.api.dto.UpdateReservationRequest;
import com.champsoft.concertbooking.reservation.application.exception.ReservationAlreadyExistsException;
import com.champsoft.concertbooking.reservation.application.exception.ReservationModificationNotAllowedException;
import com.champsoft.concertbooking.reservation.port.out.ReservationRepositoryPort;
import com.champsoft.concertbooking.reservation.domain.exception.DuplicateReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.InvalidReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;
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
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + id));
    }

    public ReservationJpaEntity register(ReservationJpaEntity reservation) {
        if (repositoryPort.findById(reservation.id).isPresent()) {
            throw new ReservationAlreadyExistsException("Reservation with ID " + reservation.id + " already exists.");
        }

        boolean exists = repositoryPort.findAll().stream()
                .anyMatch(r -> r.customerId.equals(reservation.customerId)
                        && r.showtimeId.equals(reservation.showtimeId));

        if (exists) {
            throw new DuplicateReservationException("Customer " + reservation.customerId +
                    " is already booked for showtime " + reservation.showtimeId);
        }

        return repositoryPort.save(reservation);
    }

    public ReservationJpaEntity updateReservation(String id, UpdateReservationRequest request) {
        ReservationJpaEntity existing = getById(id);

        if ("CANCELLED".equalsIgnoreCase(existing.status)) {
            throw new ReservationModificationNotAllowedException("Reservation " + id + " is already cancelled and cannot be modified.");
        }

        if (existing.status.equalsIgnoreCase(request.status())) {
            throw new InvalidReservationException("Reservation is already " + request.status());
        }

        existing.status = request.status().toUpperCase();
        return repositoryPort.save(existing);
    }

    public void deleteReservation(String id) {
        repositoryPort.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Cannot delete. Reservation not found with ID: " + id));
        repositoryPort.deleteById(id);
    }
}