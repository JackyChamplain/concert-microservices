package com.champsoft.concertbooking.reservation.api;

import com.champsoft.concertbooking.reservation.api.dto.BookConcertRequest;
import com.champsoft.concertbooking.reservation.api.dto.UpdateReservationRequest;
import com.champsoft.concertbooking.reservation.api.mapper.ReservationApiMapper;
import com.champsoft.concertbooking.reservation.application.service.ReservationOrchestrator;
import com.champsoft.concertbooking.reservation.domain.exception.DuplicateReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.InvalidReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.concertbooking.reservation.infrastructure.persistence.ReservationJpaEntity;
import com.champsoft.concertbooking.reservation.web.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BasicControllerTest {

 private final ReservationOrchestrator service = mock(ReservationOrchestrator.class);
 private final MockMvc mockMvc = MockMvcBuilders
         .standaloneSetup(new ReservationController(service))
         .setControllerAdvice(new GlobalExceptionHandler())
         .build();
 private final ObjectMapper objectMapper = new ObjectMapper();

 @Test
 void testMappingLogic() {
  BookConcertRequest request = new BookConcertRequest("r1","c-1", "con-1", "sh-1");
  ReservationJpaEntity entity = ReservationApiMapper.toEntity(request);

  assertEquals("c-1", entity.customerId);
  assertNotNull(ReservationApiMapper.toResponse(entity));
 }
 @Test
 void getAllReturnsList() throws Exception {
  var res = new ReservationJpaEntity("r-1", "c-1", "concert-1", "show-1", "ACTIVE");
  when(service.getAllReservations()).thenReturn(List.of(res));

  mockMvc.perform(get("/api/reservations"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)));
 }

 @Test
 void bookReturnsCreatedStatus() throws Exception {
  var request = new BookConcertRequest("r1", "c-1", "concert-1", "show-1");
  var res = new ReservationJpaEntity("r1", "c-1", "concert-1", "show-1", "ACTIVE");

  when(service.register(any())).thenReturn(res);

  mockMvc.perform(post("/api/reservations")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request))) // Use .content()
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id", is("r1"))); // Changed from r-1 to r1 to match 'res'
 }

 @Test
 void deleteReturnsNoContent() throws Exception {
  doNothing().when(service).deleteReservation("r-1");

  mockMvc.perform(delete("/api/reservations/r-1"))
          .andExpect(status().isNoContent());
 }

 @Test
 void handleDuplicateThrowsConflict() throws Exception {
  when(service.register(any())).thenThrow(new DuplicateReservationException("Already booked"));

  mockMvc.perform(post("/api/reservations")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(new BookConcertRequest("r1","c-1", "con-1", "sh-1"))))
          .andExpect(status().isConflict());
 }

 @Test
 void handleInvalidThrowsBadRequest() throws Exception {
  when(service.updateReservation(anyString(), any())).thenThrow(new InvalidReservationException("Bad status"));

  mockMvc.perform(put("/api/reservations/r-1")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(new UpdateReservationRequest("INVALID"))))
          .andExpect(status().isBadRequest());
 }

 @Test
 void getReservationByIdReturnsMappedJson() throws Exception {
  ReservationJpaEntity reservation = new ReservationJpaEntity("r-1", "c-1", "concert-1", "show-1", "ACTIVE");
  when(service.getById("r-1")).thenReturn(reservation);

  mockMvc.perform(get("/api/reservations/r-1"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is("r-1")))
          .andExpect(jsonPath("$.customerId", is("c-1")))
          .andExpect(jsonPath("$.concertId", is("concert-1")))
          .andExpect(jsonPath("$.showtimeId", is("show-1")))
          .andExpect(jsonPath("$.status", is("ACTIVE")));
 }

 @Test
 void updateReturnsNotFoundWhenReservationDoesNotExist() throws Exception {
  when(service.updateReservation("missing", new UpdateReservationRequest("cancelled")))
          .thenThrow(new ReservationNotFoundException("Reservation not found with ID: missing"));

  mockMvc.perform(put("/api/reservations/missing")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(new UpdateReservationRequest("cancelled"))))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.message", is("Reservation not found with ID: missing")));
 }
}
