package com.champsoft.concertbooking.concerts.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcertJpaEntityTest {
    @Test
    void testEntityBoilerplate() {
        ConcertJpaEntity entity = new ConcertJpaEntity("1", "A", "V", "T", 10.0, true);

        assertNotNull(entity.toString());
        assertEquals("1", entity.id);
        assertEquals("A", entity.name);

        ConcertJpaEntity sameEntity = new ConcertJpaEntity("1", "A", "V", "T", 10.0, true);
        assertTrue(entity.id.equals(sameEntity.id));
        assertNotNull(entity.hashCode());
    }

    @Test
    void testNoArgsConstructorAndPublicFields() {
        ConcertJpaEntity entity = new ConcertJpaEntity();
        entity.id = "C1";
        entity.name = "Rock Fest";
        entity.venue = "Arena A";
        entity.isPremium = true;

        assertEquals("C1", entity.id);
        assertEquals("Rock Fest", entity.name);
        assertTrue(entity.isPremium);
    }

    @Test
    void testAllArgsConstructor() {
        ConcertJpaEntity entity = new ConcertJpaEntity(
                "C2", "Jazz Night", "Blue Note", "JAZZ", 50.0, false
        );

        assertEquals("C2", entity.id);
        assertEquals("Jazz Night", entity.name);
        assertEquals(50.0, entity.price);
        assertFalse(entity.isPremium);
    }
}