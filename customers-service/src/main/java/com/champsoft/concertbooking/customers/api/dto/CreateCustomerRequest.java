package com.champsoft.concertbooking.customers.api.dto;

public record CreateCustomerRequest(String id, String fullName, String address, String email, String status) {}