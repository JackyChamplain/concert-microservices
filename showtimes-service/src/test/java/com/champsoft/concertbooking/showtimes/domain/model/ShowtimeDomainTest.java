package com.champsoft.concertbooking.showtimes.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ShowtimeDomainTest {

    @Test
    void testDomainModels() {
        ShowtimeId id = new ShowtimeId("S1");
        Date date = new Date(LocalDate.MAX);
        Time time = new Time(LocalTime.MAX);

        assertEquals("S1", id.value());
        assertEquals(LocalDate.MAX, date.value());
        assertEquals(LocalTime.MAX, time.value());

        Showtime showtime = new Showtime();
        assertNotNull(showtime);
    }
}