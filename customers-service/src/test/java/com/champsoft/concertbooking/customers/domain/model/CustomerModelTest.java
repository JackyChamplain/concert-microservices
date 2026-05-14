package com.champsoft.concertbooking.customers.domain.model;

import com.champsoft.concertbooking.customers.api.dto.CreateCustomerRequest;
import com.champsoft.concertbooking.customers.api.mapper.CustomerApiMapper;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerModelTest {

    @Test
    void testAddressRecord() {
        Address address = new Address("123 Main St");
        // Accessing the value ensures the generated getter/accessor is covered
        assertEquals("123 Main St", address.value());
    }

    @Test
    void testCustomerIdRecord() {
        CustomerId id = new CustomerId("C123");
        assertEquals("C123", id.value());
    }

    @Test
    void testCustomerStatusRecord() {
        CustomerStatus active = CustomerStatus.ACTIVE;
        CustomerStatus inactive = CustomerStatus.INACTIVE;

        assertEquals("ACTIVE", active.name());
        assertNotNull(inactive);
    }

    @Test
    void testCustomerDomainModel() {
        CustomerId id = new CustomerId("C1");
        Address addr = new Address("VT");
        CustomerStatus status = CustomerStatus.ACTIVE;

        Customer customer = new Customer(id, "Jacky", addr, "test@test.com", status);

        assertAll(
                () -> assertEquals("C1", customer.id.value()),
                () -> assertEquals("Jacky", customer.fullName),
                () -> assertEquals("test@test.com", customer.email),
                () -> assertEquals(CustomerStatus.ACTIVE, customer.status)
        );
    }

    @Test
    void testRecordsAndMapper() {
        // Exercise Records
        Email email = new Email("test@test.com");
        FullName name = new FullName("Jacky");
        Address addr = new Address("VT");
        CustomerId cid = new CustomerId("C1");

        assertEquals("test@test.com", email.value());
        assertEquals("Jacky", name.value());

        CustomerJpaEntity entity = new CustomerJpaEntity("C1", "Jacky", "VT", "test@test.com", "ACTIVE");
        var response = CustomerApiMapper.toResponse(entity);

        assertAll(
                () -> assertEquals("C1", response.id()),
                () -> assertEquals("Jacky", response.fullName()),
                () -> assertEquals("test@test.com", response.email())
        );

        CreateCustomerRequest req = new CreateCustomerRequest("C1", "Jacky", "VT", "test@test.com", "ACTIVE");
        assertNotNull(CustomerApiMapper.toEntity(req));
    }
}