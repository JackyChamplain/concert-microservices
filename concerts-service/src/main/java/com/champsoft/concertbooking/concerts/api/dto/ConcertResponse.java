package com.champsoft.concertbooking.concerts.api.dto;

public record ConcertResponse (
        String id,
        String name,
        String venue,
        String type,
        String entityType, double price,
        boolean isPremium
){}