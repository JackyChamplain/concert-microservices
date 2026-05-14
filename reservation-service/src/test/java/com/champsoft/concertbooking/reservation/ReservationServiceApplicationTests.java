package com.champsoft.concertbooking.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
class ReservationServiceApplicationTests {
 @Test void contextLoads() {}
 @Test
 void main() {
  // This ensures the static main method is invoked
  ReservationServiceApplication.main(new String[] {});
 }
}
