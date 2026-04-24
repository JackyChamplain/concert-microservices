package com.champsoft.concertBooking.modules.concert.api.mapper;

import com.champsoft.concertBooking.modules.concert.api.dto.*;
import com.champsoft.concertBooking.modules.concert.infrastructure.persistence.ConcertJpaEntity;

public class ConcertApiMapper {

    public static ConcertJpaEntity toEntity(CreateConcertRequest request) {
        return new ConcertJpaEntity(
                request.id(),
                request.name(),
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
                entity.getVenue(),
                entity.getType(),
                entity.getPrice(),
                entity.isPremium()
        );
    }
}