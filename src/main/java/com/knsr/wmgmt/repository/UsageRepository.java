package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {

    @Query("SELECT DISTINCT wm.user.id FROM Usage u JOIN u.meter wm WHERE u.timestamp BETWEEN :start AND :end")
    List<Long> findDistinctUserIdsWithUsage(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

    @Query("SELECT SUM(u.consumptionLiters) FROM Usage u JOIN u.meter wm WHERE wm.user.id = :userId AND u.timestamp BETWEEN :start AND :end")
    double getTotalConsumptionByUser(@Param("userId") Long userId, @Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

    boolean existsByMeterId(Long meterId);

}
