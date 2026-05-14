package com.champsoft.concertbooking.concerts.application.service;

import com.champsoft.concertbooking.concerts.application.exception.ConcertAlreadyExistsException;
import com.champsoft.concertbooking.concerts.application.exception.ConcertNotFoundException;
import com.champsoft.concertbooking.concerts.application.port.out.ConcertRepositoryPort;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidConcertNameException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidVenueNameException;
import com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertCrudServiceTest {

    @Mock
    private ConcertRepositoryPort repositoryPort;

    @InjectMocks
    private ConcertCrudService service;

    void getAll_ShouldReturnList() {
        when(repositoryPort.findAll()).thenReturn(List.of(new ConcertJpaEntity()));
        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getById_Success() {
        ConcertJpaEntity entity = new ConcertJpaEntity();
        when(repositoryPort.findById("c1")).thenReturn(Optional.of(entity));
        assertNotNull(service.getById("c1"));
    }

    @Test
    void create_Success() {
        ConcertJpaEntity entity = new ConcertJpaEntity("c1", "Rock", "Venue", "Type", 50.0, false);
        when(repositoryPort.findById("c1")).thenReturn(Optional.empty());
        when(repositoryPort.save(any())).thenReturn(entity);

        assertNotNull(service.create(entity));
        verify(repositoryPort).save(entity);
    }

    @Test
    void update_Success_CoversMapBranch() {
        ConcertJpaEntity existing = new ConcertJpaEntity();
        ConcertJpaEntity updated = new ConcertJpaEntity("c1", "New", "Venue", "Type", 60.0, true);

        when(repositoryPort.findById("c1")).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any())).thenReturn(updated);

        ConcertJpaEntity result = service.update("c1", updated);
        assertEquals("New", result.name);
    }

    @Test
    void delete_Failure_CoversIsPresentBranch() {
        when(repositoryPort.findById("missing")).thenReturn(Optional.empty());
        assertThrows(ConcertNotFoundException.class, () -> service.delete("missing"));
    }
    @Test
    void create_ShouldThrowException_WhenIdAlreadyExists() {
        ConcertJpaEntity entity = new ConcertJpaEntity();
        entity.id = "exist-1";
        when(repositoryPort.findById("exist-1")).thenReturn(Optional.of(entity));

        assertThrows(ConcertAlreadyExistsException.class, () -> service.create(entity));
    }

    @Test
    void validateConcert_ShouldThrowException_WhenNameIsBlank() {
        ConcertJpaEntity entity = new ConcertJpaEntity();
        entity.id = "new-1";
        entity.name = "";
        entity.venue = "The Arena";

        when(repositoryPort.findById("new-1")).thenReturn(Optional.empty());

        assertThrows(InvalidConcertNameException.class, () -> service.create(entity));
    }

    @Test
    void validateConcert_ShouldThrowException_WhenVenueIsNull() {
        ConcertJpaEntity entity = new ConcertJpaEntity();
        entity.id = "new-1";
        entity.name = "Rock Show";
        entity.venue = null;

        when(repositoryPort.findById("new-1")).thenReturn(Optional.empty());

        assertThrows(InvalidVenueNameException.class, () -> service.create(entity));
    }

    @Test
    void update_ShouldThrowNotFound_WhenIdDoesNotExist() {
        when(repositoryPort.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class, () ->
                service.update("missing", new ConcertJpaEntity()));
    }

    @Test
    void delete_ShouldCallRepository_WhenIdExists() {
        when(repositoryPort.findById("id-1")).thenReturn(Optional.of(new ConcertJpaEntity()));

        service.delete("id-1");

        verify(repositoryPort, times(1)).deleteById("id-1");
    }

    @Test
    void delete_ShouldThrowNotFound_WhenIdDoesNotExist() {
        when(repositoryPort.findById("ghost")).thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class, () -> service.delete("ghost"));
    }
    @Test
    void delete_Failure_CoversMissedBranch() {
        String missingId = "none";
        when(repositoryPort.findById(missingId)).thenReturn(Optional.empty());

        assertThrows(ConcertNotFoundException.class, () -> service.delete(missingId));
        verify(repositoryPort, never()).deleteById(anyString());
    }

    @Test
    void update_Success_CoversMappingBranch() {
        String id = "c1";
        ConcertJpaEntity existing = new ConcertJpaEntity();
        ConcertJpaEntity updateData = new ConcertJpaEntity(id, "Rock Fest", "Stadium", "Music", 75.0, true);

        when(repositoryPort.findById(id)).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any())).thenReturn(updateData);

        ConcertJpaEntity result = service.update(id, updateData);
        assertEquals("Rock Fest", result.name);
    }
}