package com.champsoft.concertbooking.concerts.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcertTest {
    @Test
    void validateDomainConstraints_CoversMissedBranches() {
        assertThrows(RuntimeException.class, () -> new Name(""));
        assertThrows(RuntimeException.class, () -> new Name("Ab"));
        assertThrows(RuntimeException.class, () -> new Venue("  "));

        assertThrows(IllegalArgumentException.class, () -> new Price(-10.0));
        assertThrows(IllegalArgumentException.class, () -> new ConcertId(null));
    }

    @Test
    void testDomainConcert_FullCoverage() {
        ConcertId id = new ConcertId("D1");
        Name name = new Name("Domain Test");
        Venue venue = new Venue("Main Stage");
        Type type = new Type("ROCK");
        Price price = new Price(75.0);

        Concert concert = new Concert(id, name, venue, type, price, false);

        assertAll("Ensure all domain properties are set",
                () -> assertEquals(id, concert.id),
                () -> assertEquals(name, concert.name),
                () -> assertEquals(price, concert.price),
                () -> assertFalse(concert.isPremium)
        );
    }
    @Test
    void testValueObjectBranches() {
        assertNotNull(new Price(10.0));
        assertNotNull(new Name("Valid Name"));

        assertThrows(IllegalArgumentException.class, () -> new Price(-1.0));
        assertThrows(RuntimeException.class, () -> new Name("Ab")); // Less than 3 chars
        assertThrows(RuntimeException.class, () -> new Venue("")); // Blank
        assertThrows(IllegalArgumentException.class, () -> new ConcertId(null));
    }
    @Test
    void testDomainConcert() {
        ConcertId id = new ConcertId("D1");
        Name name = new Name("Domain Test");
        Venue venue = new Venue("Main Stage");
        Type type = new Type("ROCK");
        Price price = new Price(75.0);

        Concert concert = new Concert(id, name, venue, type, price, false);

        assertEquals(id, concert.id);
        assertEquals(name, concert.name);
        assertEquals(price, concert.price);
        assertFalse(concert.isPremium);
    }
    @Test
    void price_NegativeValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Price(-1.0));
    }

    @Test
    void name_TooShort_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> new Name("Ab"));
    }

    @Test
    void venue_Blank_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> new Venue("  "));
    }

    @Test
    void concertId_Null_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new ConcertId(null));
    }
}