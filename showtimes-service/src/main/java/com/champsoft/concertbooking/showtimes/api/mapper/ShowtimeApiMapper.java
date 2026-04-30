package com.champsoft.concertbooking.showtimes.api.mapper;

import com.champsoft.concertbooking.showtimes.api.dto.*;
import com.champsoft.concertbooking.showtimes.infrastructure.persistence.ShowtimeJpaEntity;

public class ShowtimeApiMapper {

    public static ShowtimeResponse toResponse(ShowtimeJpaEntity entity) {
        return new ShowtimeResponse(
                entity.getId(),
                entity.getDate(),
                entity.getDate(),
                entity.getConcertId()
        );
    }

    public static ShowtimeJpaEntity toEntity(CreateShowtimeRequest request) {
        return new ShowtimeJpaEntity(
                request.id(),
                request.date(),
                request.time(),
                request.concertId()
        );
    }
}