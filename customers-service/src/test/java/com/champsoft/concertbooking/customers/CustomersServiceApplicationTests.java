package com.champsoft.concertbooking.customers;

import com.champsoft.concertbooking.customers.web.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomersServiceApplicationTests {

 @Test
 void contextLoads() {}

 @Test
 void testMain() {
  CustomersServiceApplication.main(new String[] {});
 }

 @Test
 void testApiErrorResponse() {
  LocalDateTime now = LocalDateTime.now();
  ApiErrorResponse error = new ApiErrorResponse("Error", 400, now, "/path");
  assertAll(
          () -> assertEquals("Error", error.getMessage()),
          () -> assertEquals(400, error.getStatus()),
          () -> assertEquals(now, error.getTimestamp()),
          () -> assertEquals("/path", error.getPath())
  );
 }
}