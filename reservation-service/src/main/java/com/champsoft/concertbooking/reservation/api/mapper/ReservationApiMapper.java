package com.champsoft.concertbooking.reservation.api.mapper;

import com.champsoft.concertbooking.reservation.api.dto.*;
import com.champsoft.concertbooking.reservation.infrastructure.persistence.*;

public class ReservationApiMapper {

    public static ReservationResponse toResponse(ReservationJpaEntity entity) {
        return new ReservationResponse(
                entity.id,
                entity.customerId,
                entity.concertId,
                entity.showtimeId,
                entity.status
        );
    }

    public static ReservationJpaEntity toEntity(BookConcertRequest request) {
        return new ReservationJpaEntity(
                request.id(),
                request.customerId(),
                request.concertId(),
                request.showtimeId(),
                "ACTIVE"
        );
    }
}