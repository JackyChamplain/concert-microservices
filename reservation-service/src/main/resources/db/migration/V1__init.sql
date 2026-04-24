

CREATE TABLE reservations (
                              id VARCHAR(50) PRIMARY KEY,
                              customer_id VARCHAR(50) NOT NULL,
                              concert_id VARCHAR(50) NOT NULL,
                              showtime_id VARCHAR(50) NOT NULL,
                              status VARCHAR(50) NOT NULL,

                              CONSTRAINT fk_res_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
                              CONSTRAINT fk_res_concert FOREIGN KEY (concert_id) REFERENCES concerts(id),
                              CONSTRAINT fk_res_showtime FOREIGN KEY (showtime_id) REFERENCES showtimes(id)
                                ON DELETE CASCADE -- OPTIONAL, DELETE IF NECESSARY (MIGHT CONFLICT WITH BUSINESS LOGIC)
);



INSERT INTO reservations VALUES
                             ('R1', 'U1', 'C1', 'S1', 'ACTIVE'),
                             ('R2', 'U2', 'C2', 'S3', 'ACTIVE'),
                             ('R3', 'U3', 'C3', 'S4', 'ACTIVE');

-- =========================
-- OPTIONAL CONSTRAINTS (ADVANCED)
-- =========================

-- Prevent duplicate reservation for same customer + showtime
-- (helps enforce rule #1)

ALTER TABLE reservations
    ADD CONSTRAINT unique_customer_showtime UNIQUE (customer_id, showtime_id);