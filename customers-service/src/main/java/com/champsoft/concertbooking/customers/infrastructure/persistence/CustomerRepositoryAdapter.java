package com.champsoft.concertbooking.customers.infrastructure.persistence;

import com.champsoft.concertbooking.customers.port.out.CustomerRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;

    public CustomerRepositoryAdapter(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerJpaEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<CustomerJpaEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public CustomerJpaEntity save(CustomerJpaEntity entity) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    // implement methods from CustomerRepositoryPort
}
