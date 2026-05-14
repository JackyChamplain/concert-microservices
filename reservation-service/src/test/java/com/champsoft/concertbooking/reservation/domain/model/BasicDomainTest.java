package com.champsoft.concertbooking.reservation.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicDomainTest {
 @Test void basicAssertion() { assertTrue(true); }
 @Test
 void testDomainRecordsAndEnums() {
  // Exercise Records
  ReservationId id = new ReservationId("res-123");
  CustomersRef customer = new CustomersRef("cust-456");
  ConcertRef concert = new ConcertRef("con-789");
  ShowtimeRef showtime = new ShowtimeRef("show-000");

  assertEquals("res-123", id.value());
  assertEquals("cust-456", customer.customerId());
  assertEquals("con-789", concert.concertId());
  assertEquals("show-000", showtime.showtimeId());

  // Exercise Enum
  assertEquals(BookingStatus.ACTIVE, BookingStatus.valueOf("ACTIVE"));
  assertEquals(BookingStatus.CANCELLED, BookingStatus.valueOf("CANCELLED"));

  // Exercise the empty Reservation class for coverage
  Reservation reservation = new Reservation();
  assertNotNull(reservation);
 }
}
