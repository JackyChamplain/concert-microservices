package com.champsoft.concertbooking.customers.api.dto;

public record CustomerResponse(String id, String fullName, String address, String email, String status) {}