package com.champsoft.concertbooking.customers.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    public String id;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    public String address;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String status;

    public CustomerJpaEntity() {}

    public CustomerJpaEntity(String id, String fullName, String address, String email, String status) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.status = status;
    }
}