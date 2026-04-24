package com.champsoft.concertbooking.customers.api.dto;

public record UpdateCustomerRequest(String fullName, String address, String email, String status) {}