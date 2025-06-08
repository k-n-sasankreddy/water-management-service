package com.dsce.wmgmt.controller;


import com.dsce.wmgmt.service.LoadService;
import com.dsce.wmgmt.service.RealTimeSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    private LoadService loadService;

    @Autowired
    private RealTimeSimulator simulator;

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
     * Endpoint to generate initial billing records for all users.
     */
    @PostMapping("/billing/init")
    public ResponseEntity<String> generateBilling() {
        loadService.generateInitialBilling();
        return ResponseEntity.ok("Initial billing generated.");
    }

    /**
     * (Optional) Manually trigger real-time simulation of usage and alerts.
     */
    @PostMapping("/simulate/usage")
    public ResponseEntity<String> simulateUsage() {
        simulator.simulateUsageAndAlerts();
        return ResponseEntity.ok("Simulated usage and alerts.");
    }

    /**
     * (Optional) Manually trigger real-time simulation of new users/meters.
     */
    @PostMapping("/simulate/users")
    public ResponseEntity<String> simulateUsers() {
        simulator.simulateNewUserAndMeter();
        return ResponseEntity.ok("Simulated new user and meter.");
    }

    /**
     * (Optional) Manually trigger monthly billing simulation.
     */
    @PostMapping("/simulate/billing")
    public ResponseEntity<String> simulateMonthlyBilling() {
        simulator.generateMonthlyBills();
        return ResponseEntity.ok("Simulated monthly billing.");
    }
}

