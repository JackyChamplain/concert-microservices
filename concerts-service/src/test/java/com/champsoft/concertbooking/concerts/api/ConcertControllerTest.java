package com.champsoft.concertbooking.concerts.api;

import com.champsoft.concertbooking.concerts.api.dto.ConcertResponse;
import com.champsoft.concertbooking.concerts.api.dto.CreateConcertRequest;
import com.champsoft.concertbooking.concerts.api.dto.UpdateConcertRequest;
import com.champsoft.concertbooking.concerts.application.service.ConcertCrudService;
import com.champsoft.concertbooking.concerts.application.exception.ConcertAlreadyExistsException;
import com.champsoft.concertbooking.concerts.application.exception.ConcertNotFoundException;
import com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("h2")
class ConcertControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ConcertCrudService service;

    @Test
    void getById_ShouldReturnConcertResponse() {
        ConcertJpaEntity entity = new ConcertJpaEntity(
                "c1", "Jazz Night", "The Blue Note", "JAZZ", 45.0, false
        );

        when(service.getById("c1")).thenReturn(entity);

        webTestClient.get()
                .uri("/api/concerts/c1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("c1")
                .jsonPath("$.name").isEqualTo("Jazz Night");
    }

    @Test
    void create_ShouldReturnCreated() {
        CreateConcertRequest request = new CreateConcertRequest(
                "c2", "New Show", "Main Stage", "ROCK", 100.0, true
        );

        ConcertJpaEntity savedEntity = new ConcertJpaEntity(
                "c2", "New Show", "Main Stage", "ROCK", 100.0, true
        );

        when(service.create(any())).thenReturn(savedEntity);

        webTestClient.post()
                .uri("/api/concerts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("New Show");
    }

    @Test
    void getById_NotFound_ShouldTriggerExceptionHandler() {
        when(service.getById("999")).thenThrow(new ConcertNotFoundException("Concert 999 not found"));

        webTestClient.get()
                .uri("/api/concerts/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Concert 999 not found")
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    void create_DuplicateId_ShouldTriggerBadRequest() {
        when(service.create(any())).thenThrow(new ConcertAlreadyExistsException("Already exists"));

        webTestClient.post()
                .uri("/api/concerts")
                .bodyValue(new CreateConcertRequest("c1", "Jazz", "Venue", "Type", 50.0, false))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void handleRuntimeException_WithNotFoundMessage_ShouldReturn404() {
        when(service.getAll()).thenThrow(new RuntimeException("Something was not found"));

        webTestClient.get()
                .uri("/api/concerts")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void handleRuntimeException_General_ShouldReturn500() {
        when(service.getAll()).thenThrow(new RuntimeException("Database exploded"));

        webTestClient.get()
                .uri("/api/concerts")
                .exchange()
                .expectStatus().is5xxServerError();
    }
    @Test
    void getAll_ShouldReturnList() {
        ConcertJpaEntity entity = new ConcertJpaEntity("c1", "Jazz Night", "The Blue Note", "JAZZ", 45.0, false);
        when(service.getAll()).thenReturn(List.of(entity));

        webTestClient.get()
                .uri("/api/concerts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ConcertResponse.class)
                .hasSize(1);
    }

    @Test
    void update_ShouldReturnUpdatedConcert() {
        UpdateConcertRequest updateRequest = new UpdateConcertRequest("c2","Updated Show", "New Venue", "POP", 120.0, true);
        ConcertJpaEntity updatedEntity = new ConcertJpaEntity("c2", "Updated Show", "New Venue", "POP", 120.0, true);

        when(service.update(eq("c2"), any())).thenReturn(updatedEntity);

        webTestClient.put()
                .uri("/api/concerts/c2")
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Updated Show");
    }

    @Test
    void delete_ShouldReturnNoContent() {
        webTestClient.delete()
                .uri("/api/concerts/c1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void handleRuntimeException_GeneralError_CoversElseBranch() {
        // Triggers the 'else' branch in handleRuntimeException (Internal Server Error)
        when(service.getAll()).thenThrow(new RuntimeException("Critical Database Error"));

        webTestClient.get()
                .uri("/api/concerts")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Critical Database Error");
    }

    @Test
    void update_Endpoint_Success() {
        UpdateConcertRequest req = new UpdateConcertRequest("c1","Updated", "Venue", "Type", 50.0, false);
        ConcertJpaEntity entity = new ConcertJpaEntity("c1", "Updated", "Venue", "Type", 50.0, false);

        when(service.update(eq("c1"), any())).thenReturn(entity);

        webTestClient.put()
                .uri("/api/concerts/c1")
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk();
    }
}