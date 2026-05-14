package com.champsoft.concertbooking.customers.api;

import com.champsoft.concertbooking.customers.api.dto.CreateCustomerRequest;
import com.champsoft.concertbooking.customers.api.mapper.CustomerApiMapper;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerApiMapperTest {
    @Test
    void testMappingLogic() {
        CreateCustomerRequest request = new CreateCustomerRequest("U1","Jacky", "VT", "jacky@example.com", "ACTIVE");
        CustomerJpaEntity entity = CustomerApiMapper.toEntity(request);

        assertEquals(request.fullName(), entity.fullName);
        assertEquals(request.email(), entity.email);
    }
    @Test
    void testToResponse() {
        CustomerJpaEntity entity = new CustomerJpaEntity("C1", "Jacky", "VT", "j@ex.com", "ACTIVE");
        var response = CustomerApiMapper.toResponse(entity);
        assertEquals("Jacky", response.fullName());
    }
}