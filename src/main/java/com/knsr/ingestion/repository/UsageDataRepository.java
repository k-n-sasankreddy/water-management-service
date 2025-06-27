package com.knsr.ingestion.repository;

import com.knsr.ingestion.dto.response.UsageResponseByLocationDTO;
import com.knsr.ingestion.entity.UsageData;
import com.knsr.ingestion.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    List<UsageData> findByMeter(WaterMeter meter);

    List<UsageData> findByMeterAndTimestampBetween(WaterMeter meter, OffsetDateTime start, OffsetDateTime end);


    // JPQL Query (Constructor Expression):
    // Make sure your DTO has a matching constructor (@AllArgsConstructor), and your entity relationships are properly mapped.
    @Query("SELECT new com.knsr.ingestion.dto.response.UsageResponseByLocationDTO(wm.location, SUM(ud.consumptionLiters)) " +
            "FROM UsageData ud JOIN WaterMeter wm ON ud.meter.id = wm.id " +
            "GROUP BY wm.location")
    List<UsageResponseByLocationDTO> findUsageGroupedByLocation();


    // Native Query Alternative: If you prefer a native SQL query (especially for complex aggregations), you could use:
    @Query(value = "SELECT wm.location AS location, SUM(ud.consumption_liters) AS totalConsumption " +
            "FROM water_mgmt.usage_data ud " +
            "JOIN water_mgmt.water_meter wm ON ud.meter_id = wm.id " +
            "GROUP BY wm.location", nativeQuery = true)
    List<UsageResponseByLocationDTO> findUsageGroupedByLocationNative();

}



