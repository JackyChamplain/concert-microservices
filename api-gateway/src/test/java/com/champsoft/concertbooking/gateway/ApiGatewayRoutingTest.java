package com.champsoft.concertbooking.gateway;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayRoutingTest {

 private static HttpServer backendServer;

 @LocalServerPort
 private int port;

 @BeforeAll
 static void startBackend() throws IOException {
  backendServer = HttpServer.create(new InetSocketAddress(0), 0);
  backendServer.createContext("/api/concerts/test-concert", ApiGatewayRoutingTest::handleConcertRequest);
  backendServer.start();
 }

 @AfterAll
 static void stopBackend() {
  if (backendServer != null) {
   backendServer.stop(0);
  }
 }

 @DynamicPropertySource
 static void registerGatewayProperties(DynamicPropertyRegistry registry) {
  ensureBackendStarted();
  registry.add("services.concerts.base-url", () -> "http://localhost:" + backendServer.getAddress().getPort());
  registry.add("services.customers.base-url", () -> "http://localhost:65531");
  registry.add("services.showtimes.base-url", () -> "http://localhost:65532");
  registry.add("services.reservations.base-url", () -> "http://localhost:65533");
 }

 @Test
 void gatewayRoutesConcertRequestsToConcertsService() {
  WebTestClient.bindToServer()
          .baseUrl("http://localhost:" + port)
          .build()
          .get()
          .uri("/api/concerts/test-concert")
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentType("application/json")
          .expectBody()
          .jsonPath("$.id").isEqualTo("test-concert")
          .jsonPath("$.name").isEqualTo("Gateway Verified");
 }

 private static void handleConcertRequest(HttpExchange exchange) throws IOException {
  byte[] body = "{\"id\":\"test-concert\",\"name\":\"Gateway Verified\"}".getBytes(StandardCharsets.UTF_8);
  exchange.getResponseHeaders().add("Content-Type", "application/json");
  exchange.sendResponseHeaders(200, body.length);
  try (OutputStream outputStream = exchange.getResponseBody()) {
   outputStream.write(body);
  }
 }

 private static void ensureBackendStarted() {
  if (backendServer == null) {
   try {
    startBackend();
   } catch (IOException ex) {
    throw new IllegalStateException("Failed to start backend stub", ex);
   }
  }
 }
}
