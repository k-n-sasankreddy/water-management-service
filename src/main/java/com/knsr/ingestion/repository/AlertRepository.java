package com.knsr.ingestion.repository;

import com.knsr.ingestion.entity.Alert;
import com.knsr.ingestion.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
  The @Repository annotation is not strictly necessary for Spring Data JPA repository interfaces because Spring Data JPA automatically detects and configures repository interfaces that extend JpaRepository or other repository interfaces. However, using @Repository can be beneficial for the following reasons:
  Exception Translation: The @Repository annotation enables the automatic translation of database-related exceptions into Spring's DataAccessException hierarchy.
  Component Scanning: While Spring Data JPA automatically detects repository interfaces, explicitly using @Repository can make the intention clearer and ensure that the repository is included in component scanning.
*/

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByMeter(WaterMeter meter);
}


