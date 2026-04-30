package com.champsoft.concertbooking.showtimes.api.dto;

import java.time.LocalDate;

public record ShowtimeResponse(
        String id,
        LocalDate date,
        java.time.LocalTime time,
        String concertId
) {}