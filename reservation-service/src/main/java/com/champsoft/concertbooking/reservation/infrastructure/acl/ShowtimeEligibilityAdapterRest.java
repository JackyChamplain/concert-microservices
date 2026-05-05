package com.champsoft.concertbooking.reservation.infrastructure.acl;

import com.champsoft.concertbooking.reservation.application.port.out.ShowtimesEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ShowtimeEligibilityAdapterRest implements ShowtimesEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.showtimes.base-url:http://localhost:8083}")
    private String showtimesBaseUrl;

    public ShowtimeEligibilityAdapterRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean showtimeExists(String showtimeId) {
        return exists(showtimesBaseUrl + "/api/showtimes/{id}", showtimeId);
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
            throw new IllegalStateException("Unable to verify showtime eligibility for id: " + id, ex);
        }
    }
}