package com.champsoft.concertbooking.customers.application.service;

import com.champsoft.concertbooking.customers.application.port.out.CustomerRepositoryPort;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCrudService {
    private final CustomerRepositoryPort port;

    public CustomerCrudService(CustomerRepositoryPort port) {
        this.port = port;
    }

    public List<CustomerJpaEntity> getAll() { return port.findAll(); }

    public CustomerJpaEntity getById(String id) {
        return port.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerJpaEntity create(CustomerJpaEntity entity) { return port.save(entity); }

    public CustomerJpaEntity update(String id, CustomerJpaEntity entity) {
        if (port.findById(id).isEmpty()) throw new RuntimeException("Customer not found");
        entity.id = id;
        return port.save(entity);
    }

    public void delete(String id) { port.deleteById(id); }
}