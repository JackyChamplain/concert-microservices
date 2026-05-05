package com.champsoft.concertbooking.reservation.infrastructure.acl;

import com.champsoft.concertbooking.reservation.application.port.out.CustomersEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomersEligibilityAdapterRest implements CustomersEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.customers.base-url:http://localhost:8082}")
    private String customersBaseUrl;

    public CustomersEligibilityAdapterRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean customerExists(String customerId) {
        return exists(customersBaseUrl + "/api/customers/{id}", customerId);
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
            throw new IllegalStateException("Unable to verify customer eligibility for id: " + id, ex);
        }
    }
}