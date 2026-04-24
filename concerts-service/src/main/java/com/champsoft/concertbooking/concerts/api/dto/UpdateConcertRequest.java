package com.champsoft.concertBooking.modules.concert.api.dto;

public record UpdateConcertRequest(
        String name,
        String venue,
        String type,
        double price,
        boolean isPremium
) {}