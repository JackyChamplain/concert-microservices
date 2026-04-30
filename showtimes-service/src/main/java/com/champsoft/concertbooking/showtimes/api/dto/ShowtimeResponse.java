package com.champsoft.concertbooking.showtimes.api.dto;

import java.time.LocalDate;

public record ShowtimeResponse(
        String id,
        LocalDate date,
        LocalDate time,
        String concertId
) {}