package com.champsoft.concertbooking.reservation.application.port.out;

public interface CustomersEligibilityPort {
    boolean customerExists(String customerId);
}