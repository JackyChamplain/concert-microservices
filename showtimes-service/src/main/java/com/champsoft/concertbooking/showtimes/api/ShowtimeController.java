package com.champsoft.concertBooking.modules.showtime.api;

import com.champsoft.concertBooking.modules.showtime.api.dto.*;
import com.champsoft.concertBooking.modules.showtime.api.mapper.ShowtimeApiMapper;
import com.champsoft.concertBooking.modules.showtime.application.service.ShowtimeCrudService;
import com.champsoft.concertBooking.modules.showtime.infrastructure.persistence.ShowtimeJpaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeCrudService service;

    public ShowtimeController(ShowtimeCrudService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShowtimeResponse> getAll() {
        return service.getAll().stream()
                .map(ShowtimeApiMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ShowtimeResponse getById(@PathVariable String id) {
        return ShowtimeApiMapper.toResponse(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShowtimeResponse create(@RequestBody CreateShowtimeRequest request) {
        var entity = ShowtimeApiMapper.toEntity(request);
        return ShowtimeApiMapper.toResponse(service.create(entity));
    }

    @PutMapping("/{id}")
    public ShowtimeResponse update(@PathVariable String id, @RequestBody UpdateShowtimeRequest request) {
        var entity = new ShowtimeJpaEntity(id, request.date(), request.time(), request.concertId());
        return ShowtimeApiMapper.toResponse(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}