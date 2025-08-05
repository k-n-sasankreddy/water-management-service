package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {
}
