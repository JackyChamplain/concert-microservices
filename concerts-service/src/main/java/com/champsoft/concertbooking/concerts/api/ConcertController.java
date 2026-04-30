package com.champsoft.concertbooking.concerts.api;

import com.champsoft.concertbooking.concerts.api.dto.*;
import com.champsoft.concertbooking.concerts.api.mapper.ConcertApiMapper;
import com.champsoft.concertbooking.concerts.application.service.ConcertCrudService;
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
        var entity = new com.champsoft.concertbooking.concerts.infrastructure.persistence.ConcertJpaEntity(
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