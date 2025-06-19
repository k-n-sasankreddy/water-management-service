package com.knsr.ingestion.service;

import com.knsr.ingestion.dto.response.AlertResponseDTO;
import com.knsr.ingestion.dto.response.UsageResponseDTO;
import com.knsr.ingestion.entity.Alert;
import com.knsr.ingestion.entity.UsageData;
import com.knsr.ingestion.entity.WaterMeter;
import com.knsr.ingestion.repository.AlertRepository;
import com.knsr.ingestion.repository.UsageDataRepository;
import com.knsr.ingestion.repository.WaterMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MonitoringService {

    @Autowired private UsageDataRepository usageRepo;
    @Autowired private AlertRepository alertRepo;
    @Autowired private WaterMeterRepository meterRepo;

    private final Random random = new Random();

    private static final double COST_PER_LITER = 5.0;

    //Option 1: Using fixedRate (in milliseconds) for every 10 seconds
    //@Scheduled(fixedRate = 10000)
    //Option 3: Using cron expression for Run Once Every Day (e.g., at midnight)
    /* This Means
    Second: 0
    Minute: 0
    Hour: 0 (midnight)
    Every day of the month
    Every month
    Every day of the week*/
    //@Scheduled(cron = "0 0 0 * * *")
    //Option 2: Using cron expression for every 10 minutes
    //@Scheduled(cron = "0 */10 * * * *")
    //Option 4: Using cron expression for every 1 hour
    @Scheduled(cron = "0 0 * * * *")
    public void simulateUsageAndAlerts() {
        List<WaterMeter> meters = meterRepo.findAll();
        for (WaterMeter meter : meters) {
            double consumption = 10 + random.nextDouble() * 90;

            UsageData usage = new UsageData();
            usage.setMeter(meter);
            usage.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            usage.setConsumptionLiters(consumption);
            usageRepo.save(usage);

            if (consumption > 80) {
                Alert alert = new Alert();
                alert.setMeter(meter);
                alert.setType("LEAK");
                alert.setMessage("High consumption detected: " + consumption + " liters");
                alert.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
                alertRepo.save(alert);
            }
        }
    }

    public List<UsageResponseDTO> getUsageByUserId(Long userId) {
        return meterRepo.findByUserId(userId).stream()
                .flatMap(meter -> usageRepo.findByMeter(meter).stream())
                .map(this::mapToUsageDTO)
                .collect(Collectors.toList());
    }

    public List<AlertResponseDTO> getAlertsByUserId(Long userId) {
        return meterRepo.findByUserId(userId).stream()
                .flatMap(meter -> alertRepo.findByMeter(meter).stream())
                .map(this::mapToAlertDTO)
                .collect(Collectors.toList());
    }

    public List<UsageResponseDTO> getAllUsage() {
        return usageRepo.findAll().stream()
                .map(this::mapToUsageDTO)
                .collect(Collectors.toList());
    }

    public List<AlertResponseDTO> getAllAlerts() {
        return alertRepo.findAll().stream()
                .map(this::mapToAlertDTO)
                .collect(Collectors.toList());
    }

    private UsageResponseDTO mapToUsageDTO(UsageData usage) {
        UsageResponseDTO dto = new UsageResponseDTO();
        dto.setMeterId(usage.getMeter().getId());
        dto.setTimestamp(usage.getTimestamp());
        dto.setConsumptionLiters(usage.getConsumptionLiters());
        dto.setCostInRupees(usage.getConsumptionLiters() * COST_PER_LITER);
        return dto;
    }

    private AlertResponseDTO mapToAlertDTO(Alert alert) {
        AlertResponseDTO dto = new AlertResponseDTO();
        dto.setMeterId(alert.getMeter().getId());
        dto.setType(alert.getType());
        dto.setMessage(alert.getMessage());
        dto.setCreatedAt(alert.getCreatedAt());
        return dto;
    }
}

