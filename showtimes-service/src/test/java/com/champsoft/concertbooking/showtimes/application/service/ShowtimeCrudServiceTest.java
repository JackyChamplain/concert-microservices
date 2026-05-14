package com.champsoft.concertbooking.showtimes.application.service;

import com.champsoft.concertbooking.showtimes.application.port.out.ShowtimeRepositoryPort;
import com.champsoft.concertbooking.showtimes.domain.exception.InvalidShowtimeException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeAlreadyExistsException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeModificationNotAllowedException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeNotFoundException;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowtimeCrudServiceTest {

    @Mock
    private ShowtimeRepositoryPort port;

    @InjectMocks
    private ShowtimeCrudService service;

    @Test
    void create_ShouldThrow_WhenIdExists() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now().plusDays(1), LocalTime.NOON, "C1");
        when(port.findById("S1")).thenReturn(Optional.of(entity));

        assertThrows(ShowtimeAlreadyExistsException.class, () -> service.create(entity));
    }

    @Test
    void create_ShouldThrow_WhenDateIsNull() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", null, LocalTime.NOON, "C1");
        assertThrows(InvalidShowtimeException.class, () -> service.create(entity));
    }

    @Test
    void create_ShouldThrow_WhenTimeIsNull() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now().plusDays(1), null, "C1");
        assertThrows(InvalidShowtimeException.class, () -> service.create(entity));
    }

    @Test
    void create_ShouldThrow_WhenShowtimeInPast() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now().minusDays(1), LocalTime.NOON, "C1");
        assertThrows(InvalidShowtimeException.class, () -> service.create(entity));
    }

    @Test
    void update_ShouldThrow_WhenNotFound() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now().plusDays(1), LocalTime.NOON, "C1");
        when(port.findById("S1")).thenReturn(Optional.empty());

        assertThrows(ShowtimeNotFoundException.class, () -> service.update("S1", entity));
    }

    @Test
    void update_ShouldThrow_WhenExistingShowtimePassed() {
        ShowtimeJpaEntity existing = new ShowtimeJpaEntity("S1", LocalDate.now().minusDays(1), LocalTime.NOON, "C1");
        ShowtimeJpaEntity updateRequest = new ShowtimeJpaEntity("S1", LocalDate.now().plusDays(1), LocalTime.NOON, "C1");

        when(port.findById("S1")).thenReturn(Optional.of(existing));

        assertThrows(ShowtimeModificationNotAllowedException.class, () -> service.update("S1", updateRequest));
    }

    @Test
    void delete_ShouldThrow_WhenExistingShowtimePassed() {
        ShowtimeJpaEntity existing = new ShowtimeJpaEntity("S1", LocalDate.now().minusDays(1), LocalTime.NOON, "C1");
        when(port.findById("S1")).thenReturn(Optional.of(existing));

        assertThrows(ShowtimeModificationNotAllowedException.class, () -> service.delete("S1"));
    }

    @Test
    void isInPast_ShouldReturnFalse_WhenDateOrTimeIsNull() {
        ShowtimeJpaEntity existing = new ShowtimeJpaEntity("S1", null, null, "C1");
        when(port.findById("S1")).thenReturn(Optional.of(existing));

        service.delete("S1");
        verify(port).deleteById("S1");
    }

    @Test
    void happyPath_CreateAndGet() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S2", LocalDate.now().plusDays(5), LocalTime.NOON, "C1");
        when(port.findById("S2")).thenReturn(Optional.empty());
        when(port.save(any())).thenReturn(entity);

        assertNotNull(service.create(entity));
        verify(port).save(entity);
    }

    @Test
    void testServiceHappyPaths() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now().plusDays(1), LocalTime.NOON, "C1");

        // Exercise getAll
        when(port.findAll()).thenReturn(List.of(entity));
        assertFalse(service.getAll().isEmpty());

        // Exercise getById success
        when(port.findById("S1")).thenReturn(Optional.of(entity));
        assertEquals(entity, service.getById("S1"));

        // Exercise successful update
        when(port.findById("S1")).thenReturn(Optional.of(entity));
        when(port.save(any())).thenReturn(entity);
        assertNotNull(service.update("S1", entity));
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        when(port.findById("999")).thenReturn(Optional.empty());
        Exception ex = assertThrows(ShowtimeNotFoundException.class, () -> service.getById("999"));
        assertTrue(ex.getMessage().contains("999"));
    }
}