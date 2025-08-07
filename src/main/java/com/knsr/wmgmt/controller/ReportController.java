package com.knsr.wmgmt.controller;

import com.knsr.wmgmt.dto.response.*;
import com.knsr.wmgmt.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard-summary")
    public ResponseEntity<DashboardSummaryResponseDTO> getDashboardSummary() {
        return ResponseEntity.ok(reportService.getDashboardSummary());
    }

/*
    @GetMapping("/monthly-usage/user")
    public ResponseEntity<List<MonthlyUsageResponseDTO>> getMonthlyUsagePerUser() {
        return ResponseEntity.ok(reportService.getMonthlyUsagePerUser());
    }
*/

    @GetMapping("/monthly-usage/zone")
    public ResponseEntity<List<ZoneMonthlyUsageResponseDTO>> getMonthlyUsagePerZone() {
        return ResponseEntity.ok(reportService.getMonthlyUsagePerZone());
    }

    @GetMapping("/alerts/recent")
    public ResponseEntity<List<RecentAlertResponseDTO>> getRecentAlerts() {
        return ResponseEntity.ok(reportService.getRecentAlerts());
    }

    @GetMapping("/billing-summary")
    public ResponseEntity<List<BillingSummaryResponseDTO>> getBillingSummary() {
        return ResponseEntity.ok(reportService.getBillingSummary());
    }

    @GetMapping("/anomalies/high-usage")
    public ResponseEntity<List<AnomalyUsageResponseDTO>> getHighUsageAnomalies() {
        return ResponseEntity.ok(reportService.getHighUsageAnomalies());
    }

    @GetMapping("/efficiency-scores")
    public ResponseEntity<List<EfficiencyScoreResponseDTO>> getEfficiencyScores() {
        return ResponseEntity.ok(reportService.getEfficiencyScores());
    }

  /*  @GetMapping("/alerts/realtime")
    public ResponseEntity<List<RealtimeAlertResponseDTO>> getRealtimeAlerts() {
        return ResponseEntity.ok(reportService.getRealtimeAlerts());
    }
*/
  /*  @GetMapping("/consumption/user-type")
    public ResponseEntity<List<UserTypeConsumptionResponseDTO>> getConsumptionByUserType() {
        return ResponseEntity.ok(reportService.getConsumptionByUserType());
    }*/

/*
    @GetMapping("/zones/high-usage")
    public ResponseEntity<List<HighUsageZoneResponseDTO>> getHighUsageZones() {
        return ResponseEntity.ok(reportService.getHighUsageZones());
    }
*/

    @GetMapping("/monthly-consumption/role")
    public ResponseEntity<List<RoleMonthlyConsumptionResponseDTO>> getMonthlyConsumptionByRole() {
        return ResponseEntity.ok(reportService.getMonthlyConsumptionByRole());
    }
}
