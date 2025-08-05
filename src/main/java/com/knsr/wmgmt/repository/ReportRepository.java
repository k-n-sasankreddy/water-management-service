package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.dto.response.HighUsageZoneResponseDTO;
import com.knsr.wmgmt.dto.response.RealtimeAlertResponseDTO;
import com.knsr.wmgmt.dto.response.UserTypeConsumptionResponseDTO;
import com.knsr.wmgmt.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Usage, Long> {

    // Summary Metrics
    @Query("SELECT COUNT(u) FROM User u")
    long countTotalUsers();

    @Query("SELECT COUNT(z) FROM Zone z")
    long countTotalZones();

    @Query("SELECT COUNT(m) FROM WaterMeter m WHERE m.status = 'active'")
    long countActiveMeters();

    @Query("SELECT SUM(u.consumptionLiters) FROM Usage u")
    Double sumTotalConsumption();

    @Query("SELECT COUNT(b) FROM Billing b WHERE b.status = 'unpaid'")
    long countPendingBills();

    // Monthly Usage by User
    @Query(value = """
        SELECT u.username, DATE_TRUNC('month', us.timestamp) AS month, SUM(us.consumption_liters)
        FROM water_mgmt.usage us
        JOIN water_mgmt.meter wm ON us.meter_id = wm.id
        JOIN water_mgmt.user u ON wm.user_id = u.id
        GROUP BY u.username, month
        ORDER BY month, u.username
        """, nativeQuery = true)
    List<Object[]> getMonthlyUsagePerUser();

    // Monthly Usage by Zone
    @Query(value = """
        SELECT z.name, DATE_TRUNC('month', us.timestamp) AS month, SUM(us.consumption_liters)
        FROM water_mgmt.usage us
        JOIN water_mgmt.meter wm ON us.meter_id = wm.id
        JOIN water_mgmt.zone z ON wm.zone_id = z.id
        GROUP BY z.name, month
        ORDER BY month, z.name
        """, nativeQuery = true)
    List<Object[]> getMonthlyUsagePerZone();

    // Recent Alerts (last 7 days)
    @Query(value = """
        SELECT a.created_at::date, a.meter_id, z.name, a.type, a.message
        FROM water_mgmt.alert a
        JOIN water_mgmt.meter wm ON a.meter_id = wm.id
        JOIN water_mgmt.zone z ON wm.zone_id = z.id
        WHERE a.created_at >= NOW() - INTERVAL '7 days'
        ORDER BY a.created_at DESC
        """, nativeQuery = true)
    List<Object[]> getRecentAlerts();

    // Billing Summary
    @Query(value = """
        SELECT u.username, b.amount, b.status
        FROM water_mgmt.billing b
        JOIN water_mgmt.user u ON b.user_id = u.id
        """, nativeQuery = true)
    List<Object[]> getBillingSummary();

    // High Usage Anomalies
    @Query(value = """
        SELECT u.username, us.timestamp, us.consumption_liters
        FROM water_mgmt.usage us
        JOIN water_mgmt.meter wm ON us.meter_id = wm.id
        JOIN water_mgmt.user u ON wm.user_id = u.id
        WHERE us.consumption_liters > 150
        ORDER BY us.timestamp DESC
        """, nativeQuery = true)
    List<Object[]> getHighUsageAnomalies();

    // Efficiency Scores
    @Query(value = """
        WITH avg_usage AS (
            SELECT u.username, AVG(us.consumption_liters) AS avg_consumption
            FROM water_mgmt.usage us
            JOIN water_mgmt.meter wm ON us.meter_id = wm.id
            JOIN water_mgmt.user u ON wm.user_id = u.id
            GROUP BY u.username
        )
        SELECT username, ROUND((1.0 / NULLIF(avg_consumption, 0))::numeric, 6) AS efficiency_score
        FROM avg_usage
        """, nativeQuery = true)
    List<Object[]> getEfficiencyScores();

    // ✅ New: Real-time Alerts (last 1 hour)
    @Query(value = """
        SELECT a.meter_id, a.type, a.message, a.created_at
        FROM water_mgmt.alert a
        WHERE a.created_at >= NOW() - INTERVAL '10 hour'
        ORDER BY a.created_at DESC
        """, nativeQuery = true)
    List<RealtimeAlertResponseDTO> findRecentAlerts(OffsetDateTime thresholdTime);

    // ✅ New: Consumption by User Type
    @Query(value = """
        SELECT u.role, SUM(us.consumption_liters)
        FROM water_mgmt.usage us
        JOIN water_mgmt.meter wm ON us.meter_id = wm.id
        JOIN water_mgmt.user u ON wm.user_id = u.id
        GROUP BY u.role
        """, nativeQuery = true)
    List<UserTypeConsumptionResponseDTO> getConsumptionByUserType();

    // ✅ New: High Usage Zones
    @Query(value = """
        SELECT z.name, AVG(us.consumption_liters), COUNT(wm.id)
        FROM water_mgmt.usage us
        JOIN water_mgmt.meter wm ON us.meter_id = wm.id
        JOIN water_mgmt.zone z ON wm.zone_id = z.id
        GROUP BY z.name
        HAVING AVG(us.consumption_liters) > :threshold
        """, nativeQuery = true)
    List<HighUsageZoneResponseDTO> getHighUsageZones(@Param("threshold") double threshold);
}

