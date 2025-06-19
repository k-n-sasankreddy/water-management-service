-- SCHEMA: water_mgmt
-- DROP SCHEMA IF EXISTS water_mgmt ;

CREATE SCHEMA IF NOT EXISTS water_mgmt AUTHORIZATION postgres;

--DROP TABLE if exists water_mgmt.usage_data;
--DROP TABLE if exists water_mgmt.alerts;
--DROP Table if exists water_mgmt.water_meter;
--DROP TABLE if exists water_mgmt.billing;
--DROP TABLE if exists water_mgmt.users;

CREATE TABLE IF NOT EXISTS water_mgmt.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    --created_at TIMESTAMP NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS water_mgmt.water_meter (
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES water_mgmt.users(id)
);

CREATE TABLE IF NOT EXISTS water_mgmt.usage_data (
    id SERIAL PRIMARY KEY,
    meter_id INT NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    --timestamp TIMESTAMP NOT NULL,
    consumption_liters FLOAT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (meter_id) REFERENCES water_mgmt.water_meter(id)
);

CREATE TABLE IF NOT EXISTS water_mgmt.alerts (
   id SERIAL PRIMARY KEY,
   meter_id INT NOT NULL,
   type VARCHAR(50) NOT NULL,
   message TEXT NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   FOREIGN KEY (meter_id) REFERENCES water_mgmt.water_meter(id)
);

CREATE TABLE IF NOT EXISTS water_mgmt.billing (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
   -- period_start DATE NOT NULL,
   -- period_end DATE NOT NULL,
    period_start TIMESTAMPTZ NOT NULL,
    period_end TIMESTAMPTZ NOT NULL,
    amount FLOAT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES water_mgmt.users(id)
);