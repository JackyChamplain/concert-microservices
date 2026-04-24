package com.champsoft.concertBooking.modules.showtime.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateShowtimeRequest(
        String id,
        LocalDate date,
        LocalTime time,
        String concertId
) {}