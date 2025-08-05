package com.knsr.wmgmt.dto.response;


import lombok.Data;

@Data
public class ZoneMonthlyUsageResponseDTO {
    private String zoneName;
   // private OffsetDateTime month;
    private String month; // instead of OffsetDateTime
    private Double totalConsumption;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
