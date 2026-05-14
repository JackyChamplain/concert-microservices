package com.champsoft.concertbooking.customers.infrastructure.persistence;

import com.champsoft.concertbooking.customers.domain.exception.CustomerNotFoundException;
import com.champsoft.concertbooking.customers.domain.exception.DuplicateEmailException;
import com.champsoft.concertbooking.customers.domain.model.Address;
import com.champsoft.concertbooking.customers.domain.model.Customer;
import com.champsoft.concertbooking.customers.domain.model.CustomerId;
import com.champsoft.concertbooking.customers.domain.model.CustomerStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaCustomerRepositoryAdapterTest {

    @Mock
    private SpringDataCustomerRepository springRepo;

    @InjectMocks
    private JpaCustomerRepositoryAdapter adapter;
    @Test
    void testAdapterMethods() {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        when(springRepo.findAll()).thenReturn(java.util.List.of(entity));
        when(springRepo.findById("1")).thenReturn(Optional.of(entity));
        when(springRepo.save(any())).thenReturn(entity);
        when(springRepo.existsByEmail(anyString())).thenReturn(true);

        assertAll(
                () -> assertFalse(adapter.findAll().isEmpty()),
                () -> assertTrue(adapter.findById("1").isPresent()),
                () -> assertNotNull(adapter.save(entity)),
                () -> assertTrue(adapter.existsByEmail("test@test.com"))
        );
        adapter.deleteById("1");
        verify(springRepo).deleteById("1");
    }

    @Test
    void testDomainModelAndExceptions() {
        Customer customer = new Customer(new CustomerId("1"), "J", new Address("A"), "e", CustomerStatus.ACTIVE);
        assertNotNull(customer.id);

        assertNotNull(new CustomerNotFoundException("err"));
        assertNotNull(new DuplicateEmailException("err"));
    }
    @Test
    void testExistsByEmail() {
        String email = "test@champlain.edu";
        adapter.existsByEmail(email);
        verify(springRepo).existsByEmail(email);
    }
    @Test
    void findAll_ShouldDelegateToSpringRepo() {
        adapter.findAll();
        verify(springRepo).findAll();
    }
}