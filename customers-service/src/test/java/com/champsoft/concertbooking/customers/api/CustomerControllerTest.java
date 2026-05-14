package com.champsoft.concertbooking.customers.api;

import com.champsoft.concertbooking.customers.api.dto.CreateCustomerRequest;
import com.champsoft.concertbooking.customers.api.dto.UpdateCustomerRequest;
import com.champsoft.concertbooking.customers.application.service.CustomerCrudService;
import com.champsoft.concertbooking.customers.domain.exception.CustomerNotFoundException;
import com.champsoft.concertbooking.customers.infrastructure.persistence.CustomerJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerCrudService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        CustomerJpaEntity customer = new CustomerJpaEntity("C1", "Jacky", "VT", "j@ex.com", "ACTIVE");
        when(service.getAll()).thenReturn(List.of(customer));
        mockMvc.perform(get("/api/customers")).andExpect(status().isOk());
    }

    @Test
    void testGetById_Success() throws Exception {
        CustomerJpaEntity customer = new CustomerJpaEntity("C1", "Jacky", "VT", "j@ex.com", "ACTIVE");
        when(service.getById("C1")).thenReturn(customer);
        mockMvc.perform(get("/api/customers/C1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("C1"));
    }

    @Test
    void testCreate() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest("C1", "Jacky", "VT", "j@ex.com", "ACTIVE");
        CustomerJpaEntity entity = new CustomerJpaEntity("C1", "Jacky", "VT", "j@ex.com", "ACTIVE");
        when(service.create(any())).thenReturn(entity);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Jacky U", "New", "j@ex.com", "ACTIVE");
        CustomerJpaEntity updated = new CustomerJpaEntity("C1", "Jacky U", "New", "j@ex.com", "ACTIVE");
        when(service.update(eq("C1"), any())).thenReturn(updated);

        mockMvc.perform(put("/api/customers/C1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete("C1");
        mockMvc.perform(delete("/api/customers/C1")).andExpect(status().isNoContent());
    }

    @Test
    void testHandleNotFound() throws Exception {
        when(service.getById("999")).thenThrow(new CustomerNotFoundException("Not Found"));
        mockMvc.perform(get("/api/customers/999")).andExpect(status().isNotFound());
    }

    @Test
    void testGetAllAndStreamingMap() throws Exception {
        CustomerJpaEntity customer = new CustomerJpaEntity("C1", "Jacky", "123 Main St", "jacky@example.com", "ACTIVE");
        when(service.getAll()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Jacky"));
    }

    @Test
    void testUpdateCustomerPath() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Jacky Updated", "New Address", "jacky@example.com", "ACTIVE");
        CustomerJpaEntity updatedEntity = new CustomerJpaEntity("C1", "Jacky Updated", "New Address", "jacky@example.com", "ACTIVE");

        when(service.update(eq("C1"), any(CustomerJpaEntity.class))).thenReturn(updatedEntity);

        mockMvc.perform(put("/api/customers/C1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jacky Updated"));
    }
    @Test
    void getById_ShouldReturn404_WhenNotFound() throws Exception {
        when(service.getById("999")).thenThrow(new CustomerNotFoundException("Not Found"));

        mockMvc.perform(get("/api/customers/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found"));
    }

    @Test
    void testAllEndpoints() throws Exception {
        CustomerJpaEntity entity = new CustomerJpaEntity("C1", "Jacky", "VT", "test@test.com", "ACTIVE");

        // GET ALL
        when(service.getAll()).thenReturn(List.of(entity));
        mockMvc.perform(get("/api/customers")).andExpect(status().isOk());

        // GET BY ID
        when(service.getById("C1")).thenReturn(entity);
        mockMvc.perform(get("/api/customers/C1")).andExpect(status().isOk());

        // CREATE
        CreateCustomerRequest createReq = new CreateCustomerRequest("C1", "Jacky", "VT", "test@test.com", "ACTIVE");
        when(service.create(any())).thenReturn(entity);
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated());

        // UPDATE
        UpdateCustomerRequest updateReq = new UpdateCustomerRequest("New Name", "New Addr", "test@test.com", "ACTIVE");
        when(service.update(eq("C1"), any())).thenReturn(entity);
        mockMvc.perform(put("/api/customers/C1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk());

        // DELETE
        doNothing().when(service).delete("C1");
        mockMvc.perform(delete("/api/customers/C1")).andExpect(status().isNoContent());
    }

    @Test
    void testGlobalExceptionHandler() throws Exception {
        when(service.getById("ERR")).thenThrow(new RuntimeException("Unexpected"));
        mockMvc.perform(get("/api/customers/ERR"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(containsString("Unexpected error")));
    }

}
