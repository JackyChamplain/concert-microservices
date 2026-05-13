package com.champsoft.concertbooking.reservation.infrastructure.acl;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConcertEligibilityAdapterRestTest {

 @Test
 void concertExistsReturnsTrueForSuccessfulLookup() {
  RestTemplate restTemplate = mock(RestTemplate.class);
  ConcertEligibilityAdapterRest adapter = new ConcertEligibilityAdapterRest(restTemplate);
  ReflectionTestUtils.setField(adapter, "concertsBaseUrl", "http://concerts-service:8080");

  when(restTemplate.getForEntity(eq("http://concerts-service:8080/api/concerts/{id}"), eq(String.class), eq("concert-1")))
          .thenReturn(ResponseEntity.ok("ok"));

  assertTrue(adapter.concertExists("concert-1"));
 }

 @Test
 void concertExistsReturnsFalseForNotFound() {
  RestTemplate restTemplate = mock(RestTemplate.class);
  ConcertEligibilityAdapterRest adapter = new ConcertEligibilityAdapterRest(restTemplate);
  ReflectionTestUtils.setField(adapter, "concertsBaseUrl", "http://concerts-service:8080");

  when(restTemplate.getForEntity(eq("http://concerts-service:8080/api/concerts/{id}"), eq(String.class), eq("missing")))
          .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

  assertFalse(adapter.concertExists("missing"));
 }

 @Test
 void concertExistsWrapsTransportFailures() {
  RestTemplate restTemplate = mock(RestTemplate.class);
  ConcertEligibilityAdapterRest adapter = new ConcertEligibilityAdapterRest(restTemplate);
  ReflectionTestUtils.setField(adapter, "concertsBaseUrl", "http://concerts-service:8080");

  when(restTemplate.getForEntity(eq("http://concerts-service:8080/api/concerts/{id}"), eq(String.class), eq("concert-1")))
          .thenThrow(new ResourceAccessException("connection refused"));

  assertThrows(IllegalStateException.class, () -> adapter.concertExists("concert-1"));
 }
}
