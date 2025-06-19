package com.knsr.ingestion.repository;

import com.knsr.ingestion.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WaterMeterRepository extends JpaRepository<WaterMeter, Long> {
    List<WaterMeter> findByUserId(Long userId);
}

