package com.dsce.wmgmt.repository;

import com.dsce.wmgmt.entity.UsageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageDataRepository extends JpaRepository<UsageData, Long> {}


