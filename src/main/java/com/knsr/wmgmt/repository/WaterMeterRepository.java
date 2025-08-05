package com.knsr.wmgmt.repository;

import com.knsr.wmgmt.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Why WaterMeter and not Meter?
Your entity class is named WaterMeter, which is mapped to the meter table in the database.
The first generic parameter in JpaRepository<T, ID> must match the entity class name, not the table name.
The second parameter (Long) matches the type of the primary key (id) in your entity.
*/

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeter, Long> {
}
