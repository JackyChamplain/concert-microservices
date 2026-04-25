package com.champsoft.concertbooking.customers.application.service;

import com.champsoft.concertbooking.customers.application.port.out.CustomerRepositoryPort;
import com.champsoft.concertbooking.customers.domain.exception.CustomerNotFoundException;
import com.champsoft.concertbooking.customers.domain.exception.DuplicateEmailException;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCrudService {
    private final CustomerRepositoryPort port;

    public CustomerCrudService(CustomerRepositoryPort port) {
        this.port = port;
    }

    public List<CustomerJpaEntity> getAll() {
        return port.findAll();
    }

    public CustomerJpaEntity getById(String id) {
        return port.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
    }

    public CustomerJpaEntity create(CustomerJpaEntity entity) {
        if (port.existsByEmail(entity.email)) {
            throw new DuplicateEmailException("Email " + entity.email + " is already in use.");
        }
        return port.save(entity);
    }

    public CustomerJpaEntity update(String id, CustomerJpaEntity entity) {
        return port.findById(id)
                .map(existing -> {
                    entity.id = id;
                    return port.save(entity);
                })
                .orElseThrow(() -> new CustomerNotFoundException("Cannot update. Customer not found with ID: " + id));
    }

    public void delete(String id) {
        if (!port.findById(id).isPresent()) {
            throw new CustomerNotFoundException("Cannot delete. Customer not found with ID: " + id);
        }
        port.deleteById(id);
    }
}