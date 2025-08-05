package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
}

