
-- Sample Data Inserts

-- Users
INSERT INTO water_mgmt.user (username, email, role) VALUES
('Alice', 'alice@example.com', 'resident'),
('Bob', 'bob@example.com', 'resident'),
('Charlie', 'charlie@example.com', 'resident');

-- Zones
INSERT INTO water_mgmt.zone (name, description) VALUES
('North', 'Northern residential zone'),
('South', 'Southern commercial zone');

-- Water Meters
INSERT INTO water_mgmt.meter (location, user_id, zone_id, status) VALUES
('123 Maple St', 1, 1, 'active'),
('456 Oak St', 2, 1, 'active'),
('789 Pine St', 3, 2, 'inactive');

-- Usage
INSERT INTO water_mgmt.usage (meter_id, timestamp, consumption_liters) VALUES
(1, '2023-01-15', 4174),
(1, '2023-02-15', 4507),
(2, '2023-01-15', 4772),
(3, '2023-06-15', 3919);

-- Alerts
INSERT INTO water_mgmt.alert (meter_id, type, message) VALUES
(2, 'Overuse', 'High usage alert'),
(3, 'Offline', 'Meter offline');

-- Billing
INSERT INTO water_mgmt.billing (user_id, period_start, period_end, amount, status) VALUES
(1, '2023-01-01', '2023-01-31', 120.50, 'paid'),
(2, '2023-01-01', '2023-01-31', 150.75, 'unpaid'),
(3, '2023-01-01', '2023-01-31', 90.00, 'paid');

-- Dashboard Queries

-- Summary Cards
SELECT COUNT(*) AS total_users FROM water_mgmt.user;
SELECT COUNT(*) AS total_zones FROM water_mgmt.zone;
SELECT COUNT(*) AS active_meters FROM water_mgmt.meter WHERE status = 'active';
SELECT SUM(consumption_liters) AS total_consumption FROM water_mgmt.usage;
SELECT COUNT(*) AS pending_bills FROM water_mgmt.billing WHERE status = 'unpaid';
SELECT COUNT(*) AS alerts_today FROM water_mgmt.alert WHERE DATE(created_at) = CURRENT_DATE;

-- Monthly Usage per User
SELECT 
    u.username,
    DATE_TRUNC('month', us.timestamp) AS month,
    SUM(us.consumption_liters) AS total_consumption
FROM 
    water_mgmt.usage us
JOIN 
    water_mgmt.meter wm ON us.meter_id = wm.id
JOIN 
    water_mgmt.user u ON wm.user_id = u.id
GROUP BY 
    u.username, month
ORDER BY 
    month, u.username;

-- Monthly Usage per Zone
SELECT 
    z.name AS zone_name,
    DATE_TRUNC('month', us.timestamp) AS month,
    SUM(us.consumption_liters) AS total_consumption
FROM 
    water_mgmt.usage us
JOIN 
    water_mgmt.meter wm ON us.meter_id = wm.id
JOIN 
    water_mgmt.zone z ON wm.zone_id = z.id
GROUP BY 
    z.name, month
ORDER BY 
    month, z.name;

-- Recent Alerts Table
SELECT 
    a.created_at::date AS date,
    a.meter_id,
    z.name AS zone,
    a.type,
    a.message
FROM 
    water_mgmt.alert a
JOIN 
    water_mgmt.meter wm ON a.meter_id = wm.id
JOIN 
    water_mgmt.zone z ON wm.zone_id = z.id
WHERE 
    a.created_at >= NOW() - INTERVAL '7 days'
ORDER BY 
    a.created_at DESC;

-- Billing Summary
SELECT 
    u.username,
    b.amount,
    b.status
FROM 
    water_mgmt.billing b
JOIN 
    water_mgmt.user u ON b.user_id = u.id;

-- Anomaly Detection (High Usage)
SELECT 
    u.username,
    us.timestamp,
    us.consumption_liters
FROM 
    water_mgmt.usage us
JOIN 
    water_mgmt.meter wm ON us.meter_id = wm.id
JOIN 
    water_mgmt.user u ON wm.user_id = u.id
WHERE 
    us.consumption_liters > 4500
ORDER BY 
    us.timestamp DESC;

-- Efficiency Score per User
WITH avg_usage AS (
    SELECT 
        u.username,
        AVG(us.consumption_liters) AS avg_consumption
    FROM 
        water_mgmt.usage us
    JOIN 
        water_mgmt.meter wm ON us.meter_id = wm.id
    JOIN 
        water_mgmt.user u ON wm.user_id = u.id
    GROUP BY 
        u.username
)
SELECT 
    username,
    ROUND(1.0 / NULLIF(avg_consumption, 0), 6) AS efficiency_score
FROM 
    avg_usage;
