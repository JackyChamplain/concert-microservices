package com.champsoft.concertbooking.reservation.application.service;

import com.champsoft.concertbooking.reservation.api.dto.UpdateReservationRequest;
import com.champsoft.concertbooking.reservation.application.port.out.ReservationRepositoryPort;
import com.champsoft.concertbooking.reservation.domain.exception.DuplicateReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.concertbooking.reservation.infrastructure.persistence.ReservationJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicServiceTest {

 @Mock
 private ReservationRepositoryPort repositoryPort;

 @InjectMocks
 private RegistrationOrchestrator orchestrator;

 @Test
 void registerRejectsDuplicateReservation() {
  ReservationJpaEntity existing = new ReservationJpaEntity("r-1", "c-1", "concert-1", "show-1", "ACTIVE");
  ReservationJpaEntity candidate = new ReservationJpaEntity("r-2", "c-1", "concert-2", "show-1", "ACTIVE");

  when(repositoryPort.findAll()).thenReturn(List.of(existing));

  assertThrows(DuplicateReservationException.class, () -> orchestrator.register(candidate));
  verify(repositoryPort, never()).save(candidate);
 }

 @Test
 void updateReservationChangesStatusAndPersistsEntity() {
  ReservationJpaEntity reservation = new ReservationJpaEntity("r-1", "c-1", "concert-1", "show-1", "ACTIVE");

  when(repositoryPort.findById("r-1")).thenReturn(Optional.of(reservation));
  when(repositoryPort.save(reservation)).thenReturn(reservation);

  ReservationJpaEntity updated = orchestrator.updateReservation("r-1", new UpdateReservationRequest("cancelled"));

  assertEquals("CANCELLED", updated.status);
  verify(repositoryPort).save(reservation);
 }

 @Test
 void deleteReservationThrowsWhenIdIsMissing() {
  when(repositoryPort.findById("missing")).thenReturn(Optional.empty());

  assertThrows(ReservationNotFoundException.class, () -> orchestrator.deleteReservation("missing"));
 }
}
