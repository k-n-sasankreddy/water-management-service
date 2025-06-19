package com.knsr.ingestion.repository;

import com.knsr.ingestion.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByUserId(Long userId);
}

