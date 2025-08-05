package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {

    @Query("SELECT u FROM Usage u " +
            "JOIN FETCH u.meter m " +
            "JOIN FETCH m.zone z")
    List<Usage> findAllWithZone();
}

