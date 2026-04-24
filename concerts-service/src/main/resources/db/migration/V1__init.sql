-- ==========================================================
-- V1__init.sql
-- Concert Booking Database Schema + Sample Data
-- ==========================================================

-- =========================
-- TABLES
-- =========================

CREATE TABLE concerts (
                          id VARCHAR(50) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          venue VARCHAR(255) NOT NULL,
                          type VARCHAR(100) NOT NULL,
                          price DECIMAL(10,2) NOT NULL,
                          is_premium BOOLEAN NOT NULL
);


-- =========================
-- SAMPLE DATA
-- =========================

-- CONCERTS
INSERT INTO concerts VALUES
                         ('C1', 'Rock Night', 'Bell Centre', 'ROCK', 120.00, TRUE),
                         ('C2', 'Jazz Evening', 'Place des Arts', 'JAZZ', 80.00, FALSE),
                         ('C3', 'Pop Festival', 'Olympic Stadium', 'POP', 150.00, TRUE);


-- =========================
-- OPTIONAL CONSTRAINTS (ADVANCED)
-- =========================

-- Prevent duplicate reservation for same customer + showtime
-- (helps enforce rule #1)

-- ALTER TABLE reservations
  --  ADD CONSTRAINT unique_customer_showtime UNIQUE (customer_id, showtime_id);