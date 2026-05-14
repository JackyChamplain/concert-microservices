package com.champsoft.concertbooking.reservation.infrastructure.acl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InfrastructureAclTest {

    private RestTemplate restTemplate;
    private ConcertEligibilityAdapterRest concertAdapter;
    private CustomersEligibilityAdapterRest customerAdapter;
    private ShowtimeEligibilityAdapterRest showtimeAdapter;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        concertAdapter = new ConcertEligibilityAdapterRest(restTemplate);
        customerAdapter = new CustomersEligibilityAdapterRest(restTemplate);
        showtimeAdapter = new ShowtimeEligibilityAdapterRest(restTemplate);

        // Setting base URLs via reflection to avoid nulls during test
        ReflectionTestUtils.setField(concertAdapter, "concertsBaseUrl", "http://localhost:8081");
        ReflectionTestUtils.setField(customerAdapter, "customersBaseUrl", "http://localhost:8082");
        ReflectionTestUtils.setField(showtimeAdapter, "showtimesBaseUrl", "http://localhost:8083");
    }

    @Test
    void testConcertExistsReturnsTrueOnSuccess() {
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        assertTrue(concertAdapter.concertExists("c1"));
    }


    @Test
    void testCustomerExistsReturnsTrueOnSuccess() {
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        assertTrue(customerAdapter.customerExists("cust-1"));
    }

    @Test
    void testCustomerExistsReturnsFalseOn404() {
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertFalse(customerAdapter.customerExists("cust-notfound"));
    }

    @Test
    void testCustomerExistsReturnsFalseOnGeneralClientError() {
        // Covers: catch (HttpClientErrorException ex)
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertFalse(customerAdapter.customerExists("cust-400"));
    }

    @Test
    void testCustomerExistsThrowsIllegalStateOnConnectionFailure() {
        // Covers: catch (RestClientException ex)
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new RestClientException("Connection Refused"));

        assertThrows(IllegalStateException.class, () -> customerAdapter.customerExists("cust-err"));
    }

    @Test
    void testShowtimeExistsReturnsTrueOnSuccess() {
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        assertTrue(showtimeAdapter.showtimeExists("show-1"));
    }

    @Test
    void testShowtimeExistsReturnsFalseOn404() {
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertFalse(showtimeAdapter.showtimeExists("show-notfound"));
    }

    @Test
    void testShowtimeExistsReturnsFalseOnGeneralClientError() {
        // Covers: catch (HttpClientErrorException ex)
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        assertFalse(showtimeAdapter.showtimeExists("show-403"));
    }

    @Test
    void testShowtimeExistsThrowsIllegalStateOnConnectionFailure() {
        // Covers: catch (RestClientException ex)
        when(restTemplate.getForEntity(anyString(), eq(String.class), anyString()))
                .thenThrow(new RestClientException("Timeout"));

        assertThrows(IllegalStateException.class, () -> showtimeAdapter.showtimeExists("show-err"));
    }
}