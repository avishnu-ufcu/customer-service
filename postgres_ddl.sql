-- Table: address
CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    line1 VARCHAR(255),
    line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100)
);

-- Table: party_identification
CREATE TABLE party_identification (
    id SERIAL PRIMARY KEY,
    party_identification_type VARCHAR(100),
    party_identification_value VARCHAR(255)
);

-- Table: party
CREATE TABLE party (
    customer_id VARCHAR(50) PRIMARY KEY
);

-- Table: customer_retail
CREATE TABLE customer_retail (
    customer_id VARCHAR(50) PRIMARY KEY REFERENCES party(customer_id),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    date_of_birth DATE,
    party_identification_id INTEGER REFERENCES party_identification(id),
    email VARCHAR(255),
    phone VARCHAR(50),
    address_id INTEGER REFERENCES address(id),
    status VARCHAR(50)
);

