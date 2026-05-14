package com.champsoft.concertbooking.reservation.web;

import com.champsoft.concertbooking.reservation.api.ReservationController;
import com.champsoft.concertbooking.reservation.application.service.ReservationOrchestrator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.champsoft.concertbooking.reservation.domain.exception.DuplicateReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;

// Static imports for Mockito
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationOrchestrator reservationService;

    @Test
    void testRegisterReservationReturns400OnInvalidInput() throws Exception {
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByIdReturns404WhenNotFound() throws Exception {
        // Force the orchestrator to throw the specific domain exception
        when(reservationService.getById("nonexistent"))
                .thenThrow(new ReservationNotFoundException("Reservation not found"));

        mockMvc.perform(get("/api/reservations/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Reservation not found"));
    }


}