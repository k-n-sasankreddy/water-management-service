package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  com.knsr.wmgmt.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    // Get all bills for a user
    List<Billing> findByUserId(Long userId);

    // Get a specific bill by ID and user
    Optional<Billing> findByIdAndUserId(Long id, Long userId);

    Optional<Billing> findTopByUserIdAndStatusInOrderByPeriodEndDesc(Long userId, List<String> statuses);

    // Check if a PAID bill exists for a user in a given period
    boolean existsByUserIdAndPeriodStartAndPeriodEndAndStatus(Long userId, OffsetDateTime periodStart, OffsetDateTime periodEnd, String status);

    // Get all bills for a user in a given period
    List<Billing> findByUserIdAndPeriodStartAndPeriodEnd(Long userId, OffsetDateTime periodStart, OffsetDateTime periodEnd);
}
