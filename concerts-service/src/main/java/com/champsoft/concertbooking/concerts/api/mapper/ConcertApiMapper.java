package com.champsoft.concertbooking.concerts.api.mapper;

import com.champsoft.concertbooking.concerts.api.dto.*;
import com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity;

public class ConcertApiMapper {

    public static ConcertJpaEntity toEntity(CreateConcertRequest request) {
        return new ConcertJpaEntity(
                null,
                request.name(),
                request.artist(),
                request.venue(),
                request.type(),
                request.price(),
                request.isPremium()
        );
    }

    public static ConcertResponse toResponse(ConcertJpaEntity entity) {
        return new ConcertResponse(
                entity.getId(),
                entity.getName(),
                entity.getArtist(),
                entity.getVenue(),
                entity.getType(),
                entity.getPrice(),
                entity.isPremium()
        );
    }
}