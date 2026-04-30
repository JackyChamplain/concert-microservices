package com.champsoft.concertbooking.concerts.api.dto;

public record UpdateConcertRequest(
        String name,
        String venue,
        String type,
        double price,
        boolean isPremium
) {}