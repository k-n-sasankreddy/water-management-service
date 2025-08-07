package com.knsr.wmgmt.service;

import com.knsr.wmgmt.repository.*;
import com.knsr.wmgmt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoadService {

    private final UserRepository userRepo;
    private final WaterMeterRepository meterRepo;
    private final UsageRepository usageRepo;
    private final AlertRepository alertRepo;
    private final ZoneRepository zoneRepo;
    private final Random random = new Random();

    private static final double ALERT_THRESHOLD = 80.0;
    private static final List<String> DEFAULT_ZONES = Arrays.asList("North", "South", "East", "West");
    private static final List<String> DEFAULT_ROLES = Arrays.asList("Residential", "Commercial", "Industrial");

    @Autowired
    public LoadService(UserRepository userRepo,
                       WaterMeterRepository meterRepo,
                       UsageRepository usageRepo,
                       AlertRepository alertRepo,
                       ZoneRepository zoneRepo) {
        this.userRepo = userRepo;
        this.meterRepo = meterRepo;
        this.usageRepo = usageRepo;
        this.alertRepo = alertRepo;
        this.zoneRepo = zoneRepo;
    }

    public boolean initializeZonesIfNeeded() {
        if (zoneRepo.count() > 0) return false;

        List<Zone> zones = DEFAULT_ZONES.stream().map(name -> {
            Zone zone = new Zone();
            zone.setName(name);
            zone.setDescription(name + " Zone");
            zone.setCreatedAt(OffsetDateTime.now());
            zone.setUpdatedAt(OffsetDateTime.now());
            return zone;
        }).collect(Collectors.toList());

        zoneRepo.saveAll(zones);
        return true;
    }

    public boolean loadInitialData(int count) {
        initializeZonesIfNeeded();

        int startIndex = userRepo.findMaxUserIndex().orElse(0) + 1;
        List<Zone> zones = zoneRepo.findAll();

        List<User> users = new ArrayList<>();
        List<WaterMeter> meters = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int userIndex = startIndex + i;

            // Create user with weighted role
            User user = new User();
            user.setUsername("user" + userIndex);
            user.setEmail("user" + userIndex + "@wmgmt.com");
            user.setRole(assignWeightedRole(i));
            user.setCreatedAt(OffsetDateTime.now());
            user.setUpdatedAt(OffsetDateTime.now());
            users.add(user);

            // Assign meter with evenly distributed zone
            WaterMeter meter = new WaterMeter();
            meter.setLocation("Location-" + user.getUsername());
            meter.setUser(user);
            meter.setZone(zones.get(i % zones.size())); // Even zone distribution
            meter.setStatus("active");
            meter.setCreatedAt(OffsetDateTime.now());
            meter.setUpdatedAt(OffsetDateTime.now());
            meters.add(meter);
        }

        userRepo.saveAll(users);
        meterRepo.saveAll(meters);

        return true;
    }

    private String assignWeightedRole(int index) {
        int mod = index % 10;
        if (mod < 7) return DEFAULT_ROLES.get(0); // Residential
        else if (mod < 9) return DEFAULT_ROLES.get(1); // Commercial
        else return DEFAULT_ROLES.get(2); // Industrial
    }


    public boolean simulateUsageAndAlertsOnce() {
        if (usageRepo.count() > 0 || alertRepo.count() > 0) return false;

        List<WaterMeter> meters = meterRepo.findAll();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final double ALERT_THRESHOLD = 160.0;

        for (WaterMeter meter : meters) {
            double consumption = 10 + random.nextDouble() * 190; // Range: 10 to 200

            Usage usage = new Usage();
            usage.setMeter(meter);
            usage.setTimestamp(now);
            usage.setConsumptionLiters((float) consumption);
            usage.setCreatedAt(now);
            usage.setUpdatedAt(now);
            usageRepo.save(usage);

            if (consumption > ALERT_THRESHOLD) {
                Alert alert = new Alert();
                alert.setMeter(meter);
                alert.setCreatedAt(now);
                alert.setUpdatedAt(now);

                // Weighted alert type based on severity
                if (consumption > 190) {
                    alert.setType("HIGH_FLOW");
                    alert.setMessage("High flow detected: " + consumption + " liters");
                } else if (consumption > 175) {
                    alert.setType("PRESSURE_DROP");
                    alert.setMessage("Pressure drop suspected: " + consumption + " liters");
                } else {
                    alert.setType("LEAK");
                    alert.setMessage("Leak suspected: " + consumption + " liters");
                }

                alertRepo.save(alert);
            }
        }
        return true;
    }

    public boolean simulateUsageAndAlertsForNewMeters() {
        List<WaterMeter> allMeters = meterRepo.findAll();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final double ALERT_THRESHOLD = 160.0;

        boolean anySimulated = false;

        for (WaterMeter meter : allMeters) {
            boolean hasUsage = usageRepo.existsByMeterId(meter.getId());
            if (hasUsage) continue;

            double consumption = 10 + random.nextDouble() * 190;

            Usage usage = new Usage();
            usage.setMeter(meter);
            usage.setTimestamp(now);
            usage.setConsumptionLiters((float) consumption);
            usage.setCreatedAt(now);
            usage.setUpdatedAt(now);
            usageRepo.save(usage);

            if (consumption > ALERT_THRESHOLD) {
                Alert alert = new Alert();
                alert.setMeter(meter);
                alert.setCreatedAt(now);
                alert.setUpdatedAt(now);

                if (consumption > 190) {
                    alert.setType("HIGH_FLOW");
                    alert.setMessage("High flow detected: " + consumption + " liters");
                } else if (consumption > 175) {
                    alert.setType("PRESSURE_DROP");
                    alert.setMessage("Pressure drop suspected: " + consumption + " liters");
                } else {
                    alert.setType("LEAK");
                    alert.setMessage("Leak suspected: " + consumption + " liters");
                }

                alertRepo.save(alert);
            }

            anySimulated = true;
        }

        return anySimulated;
    }


}
