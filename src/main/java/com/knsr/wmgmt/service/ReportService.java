package com.knsr.wmgmt.service;

import com.knsr.wmgmt.dto.response.*;

import java.util.List;

public interface ReportService {
    DashboardSummaryResponseDTO getDashboardSummary();
    List<MonthlyUsageResponseDTO> getMonthlyUsagePerUser();
    List<ZoneMonthlyUsageResponseDTO> getMonthlyUsagePerZone();
    List<RecentAlertResponseDTO> getRecentAlerts();
    List<BillingSummaryResponseDTO> getBillingSummary();
    List<AnomalyUsageResponseDTO> getHighUsageAnomalies();
    List<EfficiencyScoreResponseDTO> getEfficiencyScores();

    // New endpoints
    List<RealtimeAlertResponseDTO> getRealtimeAlerts();
    List<UserTypeConsumptionResponseDTO> getConsumptionByUserType();
    List<HighUsageZoneResponseDTO> getHighUsageZones();
}

