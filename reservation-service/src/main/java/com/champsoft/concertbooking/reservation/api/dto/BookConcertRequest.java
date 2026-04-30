package com.champsoft.concertbooking.reservation.api.dto;

import jakarta.validation.constraints.NotBlank;

public record BookConcertRequest(
        @NotBlank String id,
        @NotBlank String customerId,
        @NotBlank String concertId,
        @NotBlank String showtimeId
) {}