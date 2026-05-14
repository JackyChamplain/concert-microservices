package com.champsoft.concertbooking.showtimes.api;

import com.champsoft.concertbooking.showtimes.api.dto.CreateShowtimeRequest;
import com.champsoft.concertbooking.showtimes.api.dto.UpdateShowtimeRequest;
import com.champsoft.concertbooking.showtimes.api.mapper.ShowtimeApiMapper;
import com.champsoft.concertbooking.showtimes.application.service.ShowtimeCrudService;
import com.champsoft.concertbooking.showtimes.domain.exception.InvalidShowtimeException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeAlreadyExistsException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeModificationNotAllowedException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeNotFoundException;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowtimeController.class)
class ShowtimeControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ShowtimeCrudService service;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testCrudOperations() throws Exception {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now(), LocalTime.NOON, "CONCERT-1");

        // GET ALL
        when(service.getAll()).thenReturn(List.of(entity));
        mockMvc.perform(get("/api/showtimes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("S1"));

        // GET BY ID
        when(service.getById("S1")).thenReturn(entity);
        mockMvc.perform(get("/api/showtimes/S1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value("CONCERT-1"));

        // CREATE
        CreateShowtimeRequest createReq = new CreateShowtimeRequest("S1", LocalDate.now(), LocalTime.NOON, "CONCERT-1");
        when(service.create(any())).thenReturn(entity);
        mockMvc.perform(post("/api/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated());

        // UPDATE
        UpdateShowtimeRequest updateReq = new UpdateShowtimeRequest(LocalDate.now(), LocalTime.MAX, "CONCERT-1");
        when(service.update(eq("S1"), any())).thenReturn(entity);
        mockMvc.perform(put("/api/showtimes/S1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk());

        // DELETE
        doNothing().when(service).delete("S1");
        mockMvc.perform(delete("/api/showtimes/S1")).andExpect(status().isNoContent());
    }

    @Test
    void testExceptionHandling() throws Exception {
        // Test NotFound
        when(service.getById("999")).thenThrow(new ShowtimeNotFoundException("Not Found"));
        mockMvc.perform(get("/api/showtimes/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found"));

        // Test Unexpected Error
        when(service.getAll()).thenThrow(new RuntimeException("Crash"));
        mockMvc.perform(get("/api/showtimes"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(containsString("Unexpected error")));
    }

    @Test
    void testAdditionalExceptionHandlers() throws Exception {
        // Trigger ShowtimeAlreadyExistsException (409 Conflict)
        when(service.create(any())).thenThrow(new ShowtimeAlreadyExistsException("Already exists"));
        mockMvc.perform(post("/api/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateShowtimeRequest("S1", null, null, "C1"))))
                .andExpect(status().isConflict());

        // Trigger InvalidShowtimeException (400 Bad Request)
        when(service.getById("INV")).thenThrow(new InvalidShowtimeException("Invalid data"));
        mockMvc.perform(get("/api/showtimes/INV"))
                .andExpect(status().isBadRequest());

        // Trigger ShowtimeModificationNotAllowedException (400 Bad Request)
        doThrow(new ShowtimeModificationNotAllowedException("Too late"))
                .when(service).delete("OLD");

        mockMvc.perform(delete("/api/showtimes/OLD"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMapperUtility() {
        CreateShowtimeRequest req = new CreateShowtimeRequest("S1", LocalDate.now(), LocalTime.NOON, "C1");
        ShowtimeJpaEntity entity = ShowtimeApiMapper.toEntity(req);
        assertNotNull(ShowtimeApiMapper.toResponse(entity));
    }
}