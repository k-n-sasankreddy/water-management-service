package com.knsr.ingestion.repository;

import com.knsr.ingestion.entity.UsageData;
import com.knsr.ingestion.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface UsageDataRepository extends JpaRepository<UsageData, Long> {
    List<UsageData> findByMeter(WaterMeter meter);

    List<UsageData> findByMeterAndTimestampBetween(WaterMeter meter, OffsetDateTime start, OffsetDateTime end);

}



