package com.champsoft.concertbooking.concerts.web;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ApiErrorResponseTest {

    @Test
    void testApiErrorResponseGetters() {
        LocalDateTime now = LocalDateTime.now();
        ApiErrorResponse response = new ApiErrorResponse(
                "Not Found",
                404,
                now,
                "/api/concerts/999"
        );

        assertAll("Verify all fields in ApiErrorResponse",
                () -> assertEquals("Not Found", response.getMessage()),
                () -> assertEquals(404, response.getStatus()),
                () -> assertEquals(now, response.getTimestamp()),
                () -> assertEquals("/api/concerts/999", response.getPath())
        );
    }
}