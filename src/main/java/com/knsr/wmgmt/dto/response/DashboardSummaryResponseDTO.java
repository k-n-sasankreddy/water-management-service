package com.knsr.wmgmt.dto.response;

import lombok.Data;

@Data
public class DashboardSummaryResponseDTO {
    private long totalUsers;
    private long totalZones;
    private long activeMeters;
    private double totalConsumption;
    private long pendingBills;
    private long alertsToday;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getPendingBills() {
        return pendingBills;
    }

    public void setPendingBills(long pendingBills) {
        this.pendingBills = pendingBills;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public long getActiveMeters() {
        return activeMeters;
    }

    public void setActiveMeters(long activeMeters) {
        this.activeMeters = activeMeters;
    }

    public long getTotalZones() {
        return totalZones;
    }

    public void setTotalZones(long totalZones) {
        this.totalZones = totalZones;
    }

    public long getAlertsToday() {
        return alertsToday;
    }

    public void setAlertsToday(long alertsToday) {
        this.alertsToday = alertsToday;
    }
}

