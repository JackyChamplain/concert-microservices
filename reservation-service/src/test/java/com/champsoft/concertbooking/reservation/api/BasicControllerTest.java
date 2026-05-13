package com.champsoft.concertbooking.reservation.api;

import com.champsoft.concertbooking.reservation.api.dto.UpdateReservationRequest;
import com.champsoft.concertbooking.reservation.application.service.RegistrationOrchestrator;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.concertbooking.reservation.infrastructure.persistence.ReservationJpaEntity;
import com.champsoft.concertbooking.reservation.web.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BasicControllerTest {

 private final RegistrationOrchestrator service = mock(RegistrationOrchestrator.class);
 private final MockMvc mockMvc = MockMvcBuilders
         .standaloneSetup(new ReservationController(service))
         .setControllerAdvice(new GlobalExceptionHandler())
         .build();
 private final ObjectMapper objectMapper = new ObjectMapper();

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
