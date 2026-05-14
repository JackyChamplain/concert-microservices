package com.champsoft.concertbooking.customers.domain.model;

public class Customer {
    public CustomerId id;
    public String fullName;
    public Address address;
    public String email;
    public CustomerStatus status;

    public Customer(CustomerId id, String fullName, Address address, String email, CustomerStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.status = status;
    }
}