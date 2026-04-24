package com.champsoft.concertBooking.modules.concert.api.dto;

public record ConcertResponse (
        String id,
        String name,
        String venue,
        String type,
        double price,
        boolean isPremium
){}