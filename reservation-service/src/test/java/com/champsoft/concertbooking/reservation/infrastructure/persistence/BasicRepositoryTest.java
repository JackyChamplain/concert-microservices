package com.champsoft.concertbooking.reservation.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("h2")
class BasicRepositoryTest {
 @Test void basicRepositoryAssertion() {}
 private SpringDataReservationRepository springRepo;
 private JpaReservationRepositoryAdapter adapter;

 @BeforeEach
 void setUp() {
  springRepo = mock(SpringDataReservationRepository.class);
  adapter = new JpaReservationRepositoryAdapter(springRepo);
 }

 // --- HAPPY PATHS ---

 @Test
 void testFindAllReturnsList() {
  ReservationJpaEntity entity = new ReservationJpaEntity();
  when(springRepo.findAll()).thenReturn(List.of(entity));

  List<ReservationJpaEntity> result = adapter.findAll();

  assertEquals(1, result.size());
  verify(springRepo).findAll();
 }

 @Test
 void testFindByIdReturnsEntity() {
  String id = "res-1";
  ReservationJpaEntity entity = new ReservationJpaEntity();
  when(springRepo.findById(id)).thenReturn(Optional.of(entity));

  Optional<ReservationJpaEntity> result = adapter.findById(id);

  assertTrue(result.isPresent());
  assertEquals(entity, result.get());
 }

 @Test
 void testSaveReturnsSavedEntity() {
  ReservationJpaEntity entity = new ReservationJpaEntity();
  when(springRepo.save(entity)).thenReturn(entity);

  ReservationJpaEntity result = adapter.save(entity);

  assertNotNull(result);
  assertEquals(entity, result);
 }

 @Test
 void testDeleteByIdExecutesSuccessfully() {
  String id = "res-1";

  // void method just needs verification
  adapter.deleteById(id);

  verify(springRepo, times(1)).deleteById(id);
 }

 @Test
 void testFindAllReturnsEmptyList() {
  when(springRepo.findAll()).thenReturn(List.of());
  List<ReservationJpaEntity> result = adapter.findAll();
  assertTrue(result.isEmpty());
 }

 @Test
 void testFindByIdReturnsEmptyOptional() {
  when(springRepo.findById("missing")).thenReturn(Optional.empty());
  Optional<ReservationJpaEntity> result = adapter.findById("missing");
  assertFalse(result.isPresent());
 }
}
