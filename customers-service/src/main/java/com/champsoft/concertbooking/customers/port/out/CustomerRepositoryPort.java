package com.champsoft.concertbooking.customers.port.out;

import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {

    List<CustomerJpaEntity> findAll();

    Optional<CustomerJpaEntity> findById(String id);

    CustomerJpaEntity save(CustomerJpaEntity entity);

    void deleteById(String id);

    boolean existsByEmail(String email);
}
