package com.knsr.ingestion.controller;

import com.knsr.ingestion.dto.response.AlertResponseDTO;
import com.knsr.ingestion.dto.response.UsageResponseByLocationDTO;
import com.knsr.ingestion.dto.response.UsageResponseDTO;
import com.knsr.ingestion.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/api/wmgmt/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;


    @GetMapping("/usage/location")
    public ResponseEntity<List<UsageResponseByLocationDTO>> getUsageByLocation() {
        return ResponseEntity.ok(monitoringService.getUsageByLocation());
    }


    @GetMapping("/usage/{userId}")
    public ResponseEntity<List<UsageResponseDTO>> getUsageByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(monitoringService.getUsageByUserId(userId));
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertResponseDTO>> getAllAlerts() {
        return ResponseEntity.ok(monitoringService.getAllAlerts());
    }

    @GetMapping("/usage")
    public ResponseEntity<List<UsageResponseDTO>> getAllUsage() {
        return ResponseEntity.ok(monitoringService.getAllUsage());
    }
}
