package com.champsoft.concertbooking.reservation.infrastructure.acl;

import com.champsoft.concertbooking.reservation.application.port.out.ConcertEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ConcertEligibilityAdapterRest implements ConcertEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.concerts.base-url:http://localhost:8081}")
    private String concertsBaseUrl;

    public ConcertEligibilityAdapterRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean concertExists(String concertId) {
        return exists(concertsBaseUrl + "/api/concerts/{id}", concertId);
    }

    private boolean exists(String url, String id) {
        try {
            HttpStatusCode status = restTemplate.getForEntity(url, String.class, id).getStatusCode();
            return status.is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        } catch (HttpClientErrorException ex) {
            return false;
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to verify concert eligibility for id: " + id, ex);
        }
    }
}