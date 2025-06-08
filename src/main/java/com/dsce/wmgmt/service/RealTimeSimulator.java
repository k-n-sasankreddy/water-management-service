package com.dsce.wmgmt.service;

import com.dsce.wmgmt.entity.*;
import com.dsce.wmgmt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class RealTimeSimulator {

    @Autowired private UserRepository userRepo;
    @Autowired private WaterMeterRepository meterRepo;
    @Autowired private UsageDataRepository usageRepo;
    @Autowired private AlertRepository alertRepo;
    @Autowired private BillingRepository billingRepo;

    private final Random random = new Random();

    /**
     * Simulates new users and water meters every 10 seconds.
     */
    @Scheduled(fixedRate = 10000)
    public void simulateNewUserAndMeter() {
        User user = new User();
        user.setUsername("live_user_" + System.currentTimeMillis());
        user.setEmail("live" + System.currentTimeMillis() + "@example.com");
        user.setRole("consumer");
        user.setKeycloakId(UUID.randomUUID().toString());
        userRepo.save(user);

        WaterMeter meter = new WaterMeter();
        meter.setLocation("LiveZone-" + random.nextInt(100));
        meter.setUserId(user.getId());
        meter.setStatus("active");
        meterRepo.save(meter);
    }

    /**
     * Simulates water usage and generates alerts for high consumption.
     */
    @Scheduled(fixedRate = 10000)
    public void simulateUsageAndAlerts() {
        List<WaterMeter> meters = meterRepo.findAll();
        for (WaterMeter meter : meters) {
            // Simulate usage
            UsageData usage = new UsageData();
            usage.setMeterId(meter.getId());
            usage.setTimestamp(LocalDateTime.now());
            double consumption = 10 + random.nextDouble() * 90; // 10–100 liters
            usage.setConsumptionLiters(consumption);
            usageRepo.save(usage);

            // Generate alert if consumption is high
            if (consumption > 80) {
                Alert alert = new Alert();
                alert.setMeterId(meter.getId());
                alert.setType("LEAK");
                alert.setMessage("High consumption detected: " + consumption + " liters");
                alert.setCreatedAt(LocalDateTime.now());
                alertRepo.save(alert);
            }
        }
    }

    /**
     * (Optional) Simulates monthly billing generation.
     * This method is a placeholder and should be implemented with actual usage aggregation logic.
     */
    @Scheduled(cron = "0 0 0 1 * ?") // Run on the 1st of every month
    public void generateMonthlyBills() {
        List<User> users = userRepo.findAll();
        for (User user : users) {
            Billing bill = new Billing();
            bill.setUserId(user.getId());
            bill.setPeriodStart(LocalDate.now().minusMonths(1));
            bill.setPeriodEnd(LocalDate.now());
            bill.setAmount(500 + random.nextDouble() * 1000); // Simulated amount
            bill.setStatus("unpaid");
            billingRepo.save(bill);
        }
    }
}

