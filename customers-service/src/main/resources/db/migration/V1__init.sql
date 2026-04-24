
CREATE TABLE customers (
                           id VARCHAR(50) PRIMARY KEY,
                           full_name VARCHAR(255) NOT NULL,
                           address VARCHAR(255),
                           email VARCHAR(255) NOT NULL,
                           status VARCHAR(50) NOT NULL
);


-- =========================
-- SAMPLE DATA
-- =========================


-- CUSTOMERS
INSERT INTO customers VALUES
                          ('U1', 'John Doe', 'Montreal', 'john@example.com', 'ACTIVE'),
                          ('U2', 'Jane Smith', 'Laval', 'jane@example.com', 'ACTIVE'),
                          ('U3', 'Mike Brown', 'Quebec City', 'mike@example.com', 'ACTIVE');



-- =========================
-- OPTIONAL CONSTRAINTS (ADVANCED)
-- =========================

-- Prevent duplicate reservation for same customer + showtime
-- (helps enforce rule #1)

ALTER TABLE reservations
    ADD CONSTRAINT unique_customer_showtime UNIQUE (customer_id, showtime_id);