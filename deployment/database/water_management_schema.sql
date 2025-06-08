CREATE TABLE water_mgmt.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    keycloak_id VARCHAR(50) NOT NULL
);

CREATE TABLE water_mgmt.water_meter (
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES water_mgmt.users(id)
);

CREATE TABLE water_mgmt.usage_data (
    id SERIAL PRIMARY KEY,
    meter_id INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    consumption_liters FLOAT NOT NULL,
    FOREIGN KEY (meter_id) REFERENCES water_mgmt.water_meter(id)
);

CREATE TABLE water_mgmt.alerts (
    id SERIAL PRIMARY KEY,
    meter_id INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (meter_id) REFERENCES water_mgmt.water_meter(id)
);

CREATE TABLE water_mgmt.billing (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    amount FLOAT NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES water_mgmt.users(id)
);