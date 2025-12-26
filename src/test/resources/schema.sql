-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50)
);

-- =========================
-- PROPERTIES
-- =========================
CREATE TABLE properties (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    price DOUBLE,
    area_sq_ft DOUBLE
);

-- =========================
-- FACILITY SCORE
-- =========================
CREATE TABLE facility_score (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_proximity INT,
    hospital_proximity INT,
    transport_access INT,
    safety_score INT,
    property_id BIGINT UNIQUE,
    CONSTRAINT fk_facility_property
        FOREIGN KEY (property_id) REFERENCES properties(id)
);

-- =========================
-- RATING RESULT
-- =========================
CREATE TABLE rating_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    final_rating DOUBLE,
    rating_category VARCHAR(50),
    rated_at TIMESTAMP,
    property_id BIGINT UNIQUE,
    CONSTRAINT fk_rating_property
        FOREIGN KEY (property_id) REFERENCES properties(id)
);

-- =========================
-- RATING LOG
-- =========================
CREATE TABLE rating_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255),
    logged_at TIMESTAMP,
    property_id BIGINT,
    CONSTRAINT fk_log_property
        FOREIGN KEY (property_id) REFERENCES properties(id)
);

-- =========================
-- MANY-TO-MANY (USER ↔ PROPERTY)
-- =========================
CREATE TABLE user_property (
    user_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, property_id),
    CONSTRAINT fk_up_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_up_property FOREIGN KEY (property_id) REFERENCES properties(id)
);
