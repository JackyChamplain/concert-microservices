package com.champsoft.concertBooking.modules.reservation.api.mapper;

import com.champsoft.concertBooking.modules.reservation.api.dto.*;
import com.champsoft.concertBooking.modules.reservation.infrastructure.persistence.*;

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