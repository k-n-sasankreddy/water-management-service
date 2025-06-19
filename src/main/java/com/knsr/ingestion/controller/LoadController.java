package com.knsr.ingestion.controller;

import com.knsr.ingestion.service.LoadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/wmgmt/load")
public class LoadController {

    @Autowired
    private LoadService loadService;

    /**
     * Endpoint to load initial users and water meters.
     * @param count number of users to generate
     */
    @PostMapping("/init")
    public ResponseEntity<String> loadInitial(@RequestParam int count) {
        loadService.loadInitialData(count);
        return ResponseEntity.ok("Loaded " + count + " users and meters.");
    }

    /**
     * One-time endpoint to simulate usage and alerts.
     */
    @PostMapping("/simulate")
    public ResponseEntity<String> simulateUsageAndAlertsOnce() {
        loadService.simulateUsageAndAlertsOnce();
        return ResponseEntity.ok("Simulated usage and alerts for all meters.");
    }

}


