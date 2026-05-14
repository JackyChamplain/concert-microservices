package com.champsoft.concertbooking.concerts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
class ConcertsServiceApplicationTests {
 @Test void contextLoads() {}
 @Test
 void testMain() {
  ConcertsServiceApplication.main(new String[] {});
 }
}
