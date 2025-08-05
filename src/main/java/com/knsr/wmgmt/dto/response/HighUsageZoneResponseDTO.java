package com.knsr.wmgmt.dto.response;

import lombok.Data;

@Data
public class HighUsageZoneResponseDTO {
    private String zoneName;
    private double averageConsumptionLiters;
    private int meterCount;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(int meterCount) {
        this.meterCount = meterCount;
    }

    public double getAverageConsumptionLiters() {
        return averageConsumptionLiters;
    }

    public void setAverageConsumptionLiters(double averageConsumptionLiters) {
        this.averageConsumptionLiters = averageConsumptionLiters;
    }
}

