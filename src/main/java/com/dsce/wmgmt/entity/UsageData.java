package com.dsce.wmgmt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usage_data", schema = "water_mgmt")
public class UsageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long meterId;
    private LocalDateTime timestamp;
    private Double consumptionLiters;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getConsumptionLiters() {
        return consumptionLiters;
    }

    public void setConsumptionLiters(Double consumptionLiters) {
        this.consumptionLiters = consumptionLiters;
    }
}

