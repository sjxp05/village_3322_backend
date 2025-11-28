-- Rental Service MVP - Database Schema (PostgreSQL)
-- Generated from JPA Entities

-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    point BIGINT NOT NULL DEFAULT 0,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Stores table
CREATE TABLE stores (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL, -- ENUM: CONVENIENCE, HARDWARE
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    address VARCHAR(255) NOT NULL,
    description TEXT,
    photo_url VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'SPACE_AVAILABLE', -- ENUM: SPACE_AVAILABLE, ALMOST_FULL, FULL
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Items table
CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    store_id BIGINT NOT NULL REFERENCES stores(id),
    owner_id BIGINT REFERENCES users(id), -- NULL = store item, NOT NULL = consigned item
    name VARCHAR(255) NOT NULL,
    description TEXT,
    photo_url VARCHAR(500),
    fee_per_day BIGINT NOT NULL,
    fee_per_day BIGINT NOT NULL,
    deposit BIGINT NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE', -- ENUM: AVAILABLE, RENTED
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Reservations table (Booking stage)
CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    item_id BIGINT NOT NULL REFERENCES items(id),
    initial_paid_fee BIGINT NOT NULL,
    qr_token VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL DEFAULT 'PAID', -- ENUM: PAID, CANCELED, IN_USE, RETURNED
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Rentals table (Active usage stage)
CREATE TABLE rentals (
    reservation_id BIGINT PRIMARY KEY REFERENCES reservations(id), -- @MapsId
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    time_limit TIMESTAMP NOT NULL,
    actual_paid_fee BIGINT,
    status VARCHAR(50) NOT NULL DEFAULT 'IN_USE', -- ENUM: IN_USE, RETURNED, LOST, DAMAGED
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Consigns table (Consignment tracking)
CREATE TABLE consigns (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id),
    item_id BIGINT NOT NULL REFERENCES items(id),
    total_profit BIGINT NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE', -- ENUM: ACTIVE, WITHDRAWN, SOLD_OUT
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for better query performance
CREATE INDEX idx_items_store_id ON items(store_id);
CREATE INDEX idx_items_owner_id ON items(owner_id);
CREATE INDEX idx_items_status ON items(status);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reservations_item_id ON reservations(item_id);
CREATE INDEX idx_reservations_qr_token ON reservations(qr_token);
CREATE INDEX idx_reservations_status ON reservations(status);
CREATE INDEX idx_rentals_status ON rentals(status);
CREATE INDEX idx_consigns_owner_id ON consigns(owner_id);
CREATE INDEX idx_consigns_item_id ON consigns(item_id);

-- Sample data for testing
INSERT INTO users (nickname, email, point, latitude, longitude)
VALUES ('TestUser', 'test@example.com', 10000, 37.5665, 126.9780);

INSERT INTO stores (name, category, latitude, longitude, address, description, status)
VALUES ('Village Convenience Store', 'CONVENIENCE', 37.5660, 126.9784, 'Seoul, South Korea', 'Your friendly neighborhood store', 'SPACE_AVAILABLE');

INSERT INTO items (store_id, name, description, fee_per_day, fee_per_day, deposit, quantity, status)
VALUES (1, 'Umbrella', 'Standard umbrella for rainy days', 5000, 1000, 10000, 5, 'AVAILABLE');
