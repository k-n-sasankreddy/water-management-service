
-- 1. Summary Cards

-- a. Total Users
SELECT COUNT(*) AS total_users FROM water_mgmt.user;

-- b. Total Zones
SELECT COUNT(*) AS total_zones FROM water_mgmt.zone;

-- c. Active Meters
SELECT COUNT(*) AS active_meters FROM water_mgmt.meter WHERE status = 'active';

-- d. Total Consumption
SELECT SUM(consumption_liters) AS total_consumption FROM water_mgmt.usage;

-- e. Pending Bills
SELECT COUNT(*) AS pending_bills FROM water_mgmt.billing WHERE status = 'unpaid';

-- f. Alerts Today
SELECT COUNT(*) AS alerts_today FROM water_mgmt.alert WHERE DATE(created_at) = CURRENT_DATE;


-- 2. Monthly Usage per User
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


-- 3. Monthly Usage per Zone
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


-- 4. Recent Alerts Table
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


-- 5. Billing Summary
SELECT 
    u.username,
    b.amount,
    b.status
FROM 
    water_mgmt.billing b
JOIN 
    water_mgmt.user u ON b.user_id = u.id;


-- 6. Anomaly Detection (High Usage)
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


-- 7. Efficiency Score per User
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
