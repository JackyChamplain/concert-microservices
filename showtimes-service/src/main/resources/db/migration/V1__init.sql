

CREATE TABLE showtimes (
                           id VARCHAR(50) PRIMARY KEY,
                           date DATE NOT NULL,
                           time TIME NOT NULL,
                           concert_id VARCHAR(50)
                           -- CONSTRAINT fk_showtime_concert
                             --  FOREIGN KEY (concert_id) REFERENCES concerts(id)
);

-- =========================
-- SAMPLE DATA
-- =========================

-- SHOWTIMES


INSERT INTO showtimes VALUES
                          ('S1', '2026-06-01', '18:00:00', 'C1'), -- Bell Centre
                          ('S2', '2026-06-01', '21:00:00', 'C1'), -- Bell Centre (OK: different time)
                          ('S3', '2026-06-01', '19:00:00', 'C2'), -- Place des Arts
                          ('S4', '2026-06-02', '20:00:00', 'C3'); -- Olympic Stadium

-- RESERVATIONS
-- IMPORTANT:
-- - No customer booked twice at same time
-- - All reservations valid

-- INSERT INTO reservations VALUES
 --                            ('R1', 'U1', 'C1', 'S1', 'ACTIVE'),
  --                           ('R2', 'U2', 'C2', 'S3', 'ACTIVE'),
    --                         ('R3', 'U3', 'C3', 'S4', 'ACTIVE');

-- =========================
-- OPTIONAL CONSTRAINTS (ADVANCED)
-- =========================

-- Prevent duplicate reservation for same customer + showtime
-- (helps enforce rule #1)

-- ALTER TABLE reservations
  --  ADD CONSTRAINT unique_customer_showtime UNIQUE (customer_id, showtime_id);