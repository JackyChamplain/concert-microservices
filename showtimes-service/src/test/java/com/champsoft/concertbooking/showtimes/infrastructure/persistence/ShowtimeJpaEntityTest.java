package com.champsoft.concertbooking.showtimes.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ShowtimeJpaEntityTest {

    @Test
    void testEntityPersistenceConstructor() {
        ShowtimeJpaEntity empty = new ShowtimeJpaEntity();
        assertNull(empty.id);

        ShowtimeJpaEntity entity = new ShowtimeJpaEntity("S1", LocalDate.now(), LocalTime.NOON, "C1");
        assertEquals("S1", entity.id);
        assertEquals("C1", entity.concertId);
    }
}