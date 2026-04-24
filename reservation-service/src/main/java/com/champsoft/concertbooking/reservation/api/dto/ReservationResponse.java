package com.champsoft.concertBooking.modules.reservation.api.dto;

public record ReservationResponse(
        String id,
        String customerId,
        String concertId,
        String showtimeId,
        String status
) {}