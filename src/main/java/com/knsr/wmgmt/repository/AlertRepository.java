package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
}

