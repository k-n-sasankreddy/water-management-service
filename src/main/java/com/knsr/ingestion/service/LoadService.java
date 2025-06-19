package com.knsr.ingestion.service;

import com.knsr.ingestion.entity.*;
import com.knsr.ingestion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class LoadService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WaterMeterRepository meterRepo;

    @Autowired
    private UsageDataRepository usageRepo;

    @Autowired
    private AlertRepository alertRepo;

    private final Random random = new Random();
    private static final double ALERT_THRESHOLD = 80.0;

    /**
     * One-time load of users and water meters.
     * @param userCount number of users to generate
     */
    public void loadInitialData(int userCount) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@wmgmt.com");
            user.setRole("consumer");
            users.add(user);
        }

        userRepo.saveAll(users);

        List<WaterMeter> meters = new ArrayList<>();
        for (User user : users) {
            WaterMeter meter = new WaterMeter();
            meter.setLocation("Zone-" + random.nextInt(100));
            meter.setUser(user);
            meter.setStatus("active");
            meters.add(meter);
        }

        meterRepo.saveAll(meters);
    }

    /**
     * One-time simulation of water usage and alert generation.
     */
    public void simulateUsageAndAlertsOnce() {
        List<WaterMeter> meters = meterRepo.findAll();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        for (WaterMeter meter : meters) {
            double consumption = 10 + random.nextDouble() * 90;

            UsageData usage = new UsageData();
            usage.setMeter(meter);
            usage.setTimestamp(now);
            usage.setConsumptionLiters(consumption);
            usageRepo.save(usage);

            if (consumption > ALERT_THRESHOLD) {
                Alert alert = new Alert();
                alert.setMeter(meter);
                alert.setType("LEAK");
                alert.setMessage("High consumption detected: " + consumption + " liters");
                alert.setCreatedAt(now);
                alertRepo.save(alert);
            }
        }
    }
}
