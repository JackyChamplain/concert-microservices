package com.champsoft.concertbooking.concerts.api.dto;

public record CreateConcertRequest (

    String id,
    String name,
    String artist,
    String venue,
    String type,
    double price,
    boolean isPremium

){}