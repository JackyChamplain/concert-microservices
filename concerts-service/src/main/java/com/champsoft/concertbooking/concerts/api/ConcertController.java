package com.champsoft.concertBooking.modules.concert.api;

import com.champsoft.concertBooking.modules.concert.api.dto.*;
import com.champsoft.concertBooking.modules.concert.api.mapper.ConcertApiMapper;
import com.champsoft.concertBooking.modules.concert.application.service.ConcertCrudService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertCrudService service;

    public ConcertController(ConcertCrudService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConcertResponse> getAll() {
        return service.getAll().stream()
                .map(ConcertApiMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ConcertResponse getById(@PathVariable String id) {
        return ConcertApiMapper.toResponse(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConcertResponse create(@RequestBody CreateConcertRequest request) {
        return ConcertApiMapper.toResponse(
                service.create(ConcertApiMapper.toEntity(request))
        );
    }

    @PutMapping("/{id}")
    public ConcertResponse update(@PathVariable String id, @RequestBody UpdateConcertRequest request) {
        var entity = new com.champsoft.concertBooking.modules.concert.infrastructure.persistence.ConcertJpaEntity(
                id, request.name(), request.venue(), request.type(), request.price(), request.isPremium()
        );
        return ConcertApiMapper.toResponse(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}