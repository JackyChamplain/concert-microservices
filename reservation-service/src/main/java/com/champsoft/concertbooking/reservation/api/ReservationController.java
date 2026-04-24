package com.champsoft.concertBooking.modules.reservation.api;

import com.champsoft.concertBooking.modules.reservation.api.dto.*;
import com.champsoft.concertBooking.modules.reservation.api.mapper.ReservationApiMapper;
import com.champsoft.concertBooking.modules.reservation.application.service.RegistrationOrchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final RegistrationOrchestrator service;

    public ReservationController(RegistrationOrchestrator service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponse> getAll() {
        return service.getAllReservations().stream()
                .map(ReservationApiMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ReservationResponse getById(@PathVariable String id) {
        return ReservationApiMapper.toResponse(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse book(@RequestBody BookConcertRequest request) {
        var reservation = ReservationApiMapper.toEntity(request);
        return ReservationApiMapper.toResponse(service.register(reservation));
    }

    @PutMapping("/{id}")
    public ReservationResponse update(@PathVariable String id, @RequestBody UpdateReservationRequest request) {
        return ReservationApiMapper.toResponse(service.updateReservation(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteReservation(id);
    }
}