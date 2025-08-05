package com.knsr.wmgmt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Data
@Entity
@Table(name = "usage", schema = "water_mgmt")
public class Usage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeter meter;

    private OffsetDateTime timestamp;

    @Column(name = "consumption_liters", nullable = false)
    private double consumptionLiters;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now =  OffsetDateTime.now(ZoneOffset.UTC);
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt =  OffsetDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getConsumptionLiters() {
        return consumptionLiters;
    }

    public void setConsumptionLiters(double consumptionLiters) {
        this.consumptionLiters = consumptionLiters;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public WaterMeter getMeter() {
        return meter;
    }

    public void setMeter(WaterMeter meter) {
        this.meter = meter;
    }
}
