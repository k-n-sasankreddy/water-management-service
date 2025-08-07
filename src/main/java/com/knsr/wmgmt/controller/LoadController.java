package com.knsr.wmgmt.controller;


import com.knsr.wmgmt.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/wmgmt")
public class LoadController {

    @Autowired
    private LoadService loadService;

    /*@PostMapping("/bootstrap/zones")
    public ResponseEntity<String> initializeZones() {
        boolean initialized = loadService.initializeZonesIfNeeded();
        if (initialized) {
            return ResponseEntity.ok("Zones initialized successfully.");
        } else {
            return ResponseEntity.ok("Zones already exist. Skipped.");
        }
    }*/

    // POST http://localhost:8070/v1/api/wmgmt/bootstrap/users-meters?count={{$random.integer(100)}}
    @PostMapping("/bootstrap/users-meters")
    public ResponseEntity<String> loadInitial(@RequestParam int count) {
        boolean loaded = loadService.loadInitialData(count);
        if (loaded) {
            return ResponseEntity.ok("Successfully loaded " + count + " new users and meters.");
        } else {
            return ResponseEntity.ok("Initial data already exists or partially loaded. Skipped.");
        }
    }

    @PostMapping("/simulate/usage-alerts")
    public ResponseEntity<String> simulateUsageAndAlertsOnce() {
        boolean simulated = loadService.simulateUsageAndAlertsOnce();
        if (simulated) {
            return ResponseEntity.ok("Usage and alerts simulated successfully.");
        } else {
            return ResponseEntity.ok("Simulation already performed. Skipped.");
        }
    }

    @PostMapping("/simulate/usage-alerts/new")
    public ResponseEntity<String> simulateUsageAndAlertsForNewMeters() {
        boolean simulated = loadService.simulateUsageAndAlertsForNewMeters();
        if (simulated) {
            return ResponseEntity.ok("Simulation completed for newly added meters.");
        } else {
            return ResponseEntity.ok("No new meters found for simulation. Skipped.");
        }
    }
}



