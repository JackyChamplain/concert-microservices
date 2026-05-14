package com.champsoft.concertbooking.showtimes.infrastructure.persistence;

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
class JpaShowtimeRepositoryAdapterTest {

    @Mock
    private SpringDataShowtimeRepository springRepo;

    @InjectMocks
    private JpaShowtimeRepositoryAdapter adapter;

    @Test
    void testAllAdapterMethods() {
        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now(), LocalTime.NOON, "C1");

        when(springRepo.save(entity)).thenReturn(entity);
        assertEquals(entity, adapter.save(entity));

        when(springRepo.findAll()).thenReturn(List.of(entity));
        assertFalse(adapter.findAll().isEmpty());

        when(springRepo.findById("S1")).thenReturn(Optional.of(entity));
        assertTrue(adapter.findById("S1").isPresent());

        doNothing().when(springRepo).deleteById("S1");
        adapter.deleteById("S1");
        verify(springRepo).deleteById("S1");
    }
}