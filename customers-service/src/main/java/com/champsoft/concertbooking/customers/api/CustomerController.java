package com.champsoft.concertbooking.customers.api;

import com.champsoft.concertbooking.customers.api.dto.*;
import com.champsoft.concertbooking.customers.api.mapper.CustomerApiMapper;
import com.champsoft.concertbooking.customers.application.service.CustomerCrudService;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerCrudService service;

    public CustomerController(CustomerCrudService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerResponse> getAll() {
        return service.getAll().stream()
                .map(CustomerApiMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable String id) {
        return CustomerApiMapper.toResponse(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody CreateCustomerRequest request) {
        var entity = CustomerApiMapper.toEntity(request);
        return CustomerApiMapper.toResponse(service.create(entity));
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable String id, @RequestBody UpdateCustomerRequest request) {
        var entity = new CustomerJpaEntity(id, request.fullName(), request.address(), request.email(), request.status());
        return CustomerApiMapper.toResponse(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}