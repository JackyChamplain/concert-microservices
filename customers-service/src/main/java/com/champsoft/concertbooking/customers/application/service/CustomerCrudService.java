package com.champsoft.concertbooking.customers.application.service;

import com.champsoft.concertbooking.customers.exception.CustomerAlreadyExistsException;
import com.champsoft.concertbooking.customers.exception.CustomerEmailAlreadyInUseException;
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
        if (port.findById(entity.id).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with ID " + entity.id + " already exists.");
        }

        if (port.existsByEmail(entity.email)) {
            throw new DuplicateEmailException("Email " + entity.email + " is already in use.");
        }

        return port.save(entity);
    }

    public CustomerJpaEntity update(String id, CustomerJpaEntity entity) {
        CustomerJpaEntity existing = port.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cannot update. Customer not found with ID: " + id));

        boolean emailTakenByAnotherCustomer = port.findAll().stream()
                .anyMatch(customer -> !customer.id.equals(id) && customer.email.equalsIgnoreCase(entity.email));
        if (emailTakenByAnotherCustomer) {
            throw new CustomerEmailAlreadyInUseException("Email " + entity.email + " is already in use by another customer.");
        }

        entity.id = existing.id;
        return port.save(entity);
    }

    public void delete(String id) {
        port.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cannot delete. Customer not found with ID: " + id));
        port.deleteById(id);
    }
}