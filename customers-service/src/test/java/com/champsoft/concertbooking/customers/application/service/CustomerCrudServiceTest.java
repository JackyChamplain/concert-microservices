package com.champsoft.concertbooking.customers.application.service;

import com.champsoft.concertbooking.customers.application.port.out.CustomerRepositoryPort;
import com.champsoft.concertbooking.customers.domain.exception.CustomerNotFoundException;
import com.champsoft.concertbooking.customers.domain.exception.DuplicateEmailException;
import com.champsoft.concertbooking.customers.exception.CustomerAlreadyExistsException;
import com.champsoft.concertbooking.customers.exception.CustomerEmailAlreadyInUseException;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerCrudServiceTest {

    @Mock
    private CustomerRepositoryPort port;

    @InjectMocks
    private CustomerCrudService service;

    @Test
    void create_ShouldThrowDuplicateEmail() {
        CustomerJpaEntity entity = new CustomerJpaEntity("C2", "Jacky", "VT", "dup@test.com", "ACTIVE");
        when(port.findById("C2")).thenReturn(Optional.empty());
        when(port.existsByEmail("dup@test.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> service.create(entity));
    }

    @Test
    void create_ShouldSave_WhenValid() {
        CustomerJpaEntity entity = new CustomerJpaEntity("C3", "New", "VT", "new@test.com", "ACTIVE");
        when(port.findById("C3")).thenReturn(Optional.empty());
        when(port.existsByEmail("new@test.com")).thenReturn(false);
        when(port.save(entity)).thenReturn(entity);

        assertNotNull(service.create(entity));
    }
    @Test
    void create_ShouldThrowException_WhenIdExists() {
        CustomerJpaEntity entity = new CustomerJpaEntity("C1", "Jacky", "VT", "test@test.com", "ACTIVE");
        when(port.findById("C1")).thenReturn(Optional.of(entity));

        assertThrows(CustomerAlreadyExistsException.class, () -> service.create(entity));
    }
    @Test
    void update_ShouldThrowException_WhenEmailTakenByOtherCustomer() {
        String existingId = "C1";
        String otherId = "C2";
        String sharedEmail = "duplicate@example.com";

        CustomerJpaEntity existing = new CustomerJpaEntity(existingId, "Jacky", "VT", sharedEmail, "ACTIVE");
        CustomerJpaEntity otherCustomer = new CustomerJpaEntity(otherId, "Someone Else", "NY", sharedEmail, "ACTIVE");
        CustomerJpaEntity updateRequest = new CustomerJpaEntity(existingId, "Jacky New", "VT", sharedEmail, "ACTIVE");

        when(port.findById(existingId)).thenReturn(Optional.of(existing));
        when(port.findAll()).thenReturn(List.of(existing, otherCustomer));

        assertThrows(CustomerEmailAlreadyInUseException.class, () -> service.update(existingId, updateRequest));
    }

    @Test
    void getById_ShouldReturnCustomer_WhenExists() {
        CustomerJpaEntity customer = new CustomerJpaEntity("C1", "Jacky", "VT", "test@test.com", "ACTIVE");
        when(port.findById("C1")).thenReturn(Optional.of(customer));

        CustomerJpaEntity result = service.getById("C1");

        assertEquals("Jacky", result.fullName);
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        when(port.findById("C1")).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> service.getById("C1"));
    }

    @Test
    void delete_ShouldCallPort_WhenExists() {
        when(port.findById("C1")).thenReturn(Optional.of(new CustomerJpaEntity()));
        service.delete("C1");
        verify(port).deleteById("C1");
    }
}