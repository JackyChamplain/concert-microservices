package com.champsoft.concertbooking.customers.infrastructure.persistence;

import com.champsoft.concertbooking.customers.application.port.out.CustomerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository springRepo;

    public JpaCustomerRepositoryAdapter(SpringDataCustomerRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public List<CustomerJpaEntity> findAll() {
        return springRepo.findAll();
    }

    @Override
    public Optional<CustomerJpaEntity> findById(String id) {
        return springRepo.findById(id);
    }

    @Override
    public CustomerJpaEntity save(CustomerJpaEntity entity) {
        return springRepo.save(entity);
    }

    @Override
    public void deleteById(String id) {
        springRepo.deleteById(id);
    }
}