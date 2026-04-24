package com.champsoft.concertbooking.reservation.api.dto;

public record ReservationResponse(
        String id,
        String customerId,
        String concertId,
        String showtimeId,
        String status
) {}