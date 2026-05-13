package com.champsoft.concertbooking.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

 @Bean
 RouteLocator gatewayRoutes(
         RouteLocatorBuilder builder,
         @Value("${services.concerts.base-url:http://localhost:8081}") String concertsBaseUrl,
         @Value("${services.customers.base-url:http://localhost:8082}") String customersBaseUrl,
         @Value("${services.showtimes.base-url:http://localhost:8083}") String showtimesBaseUrl,
         @Value("${services.reservations.base-url:http://localhost:8085}") String reservationsBaseUrl) {
  return builder.routes()
          .route("concerts-service", r -> r.path("/api/concerts/**").uri(concertsBaseUrl))
          .route("customers-service", r -> r.path("/api/customers/**").uri(customersBaseUrl))
          .route("showtimes-service", r -> r.path("/api/showtimes/**").uri(showtimesBaseUrl))
          .route("reservation-service", r -> r.path("/api/reservations/**").uri(reservationsBaseUrl))
          .build();
 }
}
