package com.champsoft.concertbooking.concerts.infrastructure.persistence;

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
class JpaConcertRepositoryAdapterTest {

    @Mock
    private SpringDataConcertRepository springRepo;

    @InjectMocks
    private JpaConcertRepositoryAdapter adapter;

    @Test
    void testAdapterDelegation() {
        ConcertJpaEntity entity = new ConcertJpaEntity("1", "Rock", "Arena", "ROCK", 50.0, false);

        when(springRepo.save(any())).thenReturn(entity);
        when(springRepo.findAll()).thenReturn(List.of(entity));
        when(springRepo.findById("1")).thenReturn(Optional.of(entity));

        ConcertJpaEntity saved = adapter.save(entity);
        assertNotNull(saved);

        assertFalse(adapter.findAll().isEmpty());

        assertTrue(adapter.findById("1").isPresent());

        adapter.deleteById("1");

        verify(springRepo, times(1)).save(entity);
        verify(springRepo, times(1)).findAll();
        verify(springRepo, times(1)).findById("1");
        verify(springRepo, times(1)).deleteById("1");
    }
}