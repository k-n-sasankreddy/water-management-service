-- SCHEMA: water_mgmt
-- DROP SCHEMA IF EXISTS water_mgmt ;

-- 1. Schema Creation
CREATE SCHEMA IF NOT EXISTS water_mgmt AUTHORIZATION postgres;

/*
Order of Table Creation
This order respects foreign key dependencies:
user – no dependencies
zone – no dependencies
meter – depends on user and zone
usage – depends on meter
alert – depends on meter
billing – depends on user
*/

/*Truncate All Data (in reverse dependency order)
TRUNCATE TABLE
    water_mgmt.billing,
    water_mgmt.alert,
    water_mgmt.usage,
    water_mgmt.meter,
    water_mgmt.zone,
    water_mgmt.user
RESTART IDENTITY CASCADE;
*/

/*Drop All Tables (in reverse dependency order)
DROP TABLE IF EXISTS
    water_mgmt.billing,
    water_mgmt.alert,
    water_mgmt.usage,
    water_mgmt.meter,
    water_mgmt.zone,
    water_mgmt.user
    CASCADE;
*/

-- 2. Table Creation (in dependency order)
-- user table
CREATE TABLE IF NOT EXISTS water_mgmt.user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- zone table
CREATE TABLE IF NOT EXISTS water_mgmt.zone (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- meter table
CREATE TABLE IF NOT EXISTS water_mgmt.meter (
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    user_id INT NOT NULL,
    zone_id INT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES water_mgmt.user(id),
    FOREIGN KEY (zone_id) REFERENCES water_mgmt.zone(id)
);

-- usage table
CREATE TABLE IF NOT EXISTS water_mgmt.usage (
    id SERIAL PRIMARY KEY,
    meter_id INT NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    consumption_liters FLOAT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (meter_id) REFERENCES water_mgmt.meter(id)
);

-- alert table
CREATE TABLE IF NOT EXISTS water_mgmt.alert (
    id SERIAL PRIMARY KEY,
    meter_id INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (meter_id) REFERENCES water_mgmt.meter(id)
);

-- billing table
CREATE TABLE IF NOT EXISTS water_mgmt.billing (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    period_start TIMESTAMPTZ NOT NULL,
    period_end TIMESTAMPTZ NOT NULL,
    amount FLOAT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES water_mgmt.user(id)
);
