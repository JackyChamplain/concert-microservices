package com.champsoft.concertbooking.concerts.application.service;

import com.champsoft.concertbooking.concerts.application.service.exception.ConcertAlreadyExistsException;
import com.champsoft.concertbooking.concerts.application.service.exception.ConcertNotFoundException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidConcertNameException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidVenueNameException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {
    @Test
    void testExceptionConstructors() {
        assertNotNull(new ConcertAlreadyExistsException("error"));
        assertNotNull(new ConcertNotFoundException("error"));
        assertNotNull(new InvalidConcertNameException("error"));
        assertNotNull(new InvalidVenueNameException("error"));
    }
}