package com.knsr.ingestion.service;

import com.knsr.ingestion.dto.request.MeterStatusUpdateRequestDTO;
import com.knsr.ingestion.dto.request.UserRequestDTO;
import com.knsr.ingestion.dto.request.WaterUsageRequestDTO;
import com.knsr.ingestion.entity.Alert;
import com.knsr.ingestion.entity.UsageData;
import com.knsr.ingestion.entity.User;
import com.knsr.ingestion.entity.WaterMeter;
import com.knsr.ingestion.repository.AlertRepository;
import com.knsr.ingestion.repository.UsageDataRepository;
import com.knsr.ingestion.repository.UserRepository;
import com.knsr.ingestion.repository.WaterMeterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WaterMeterRepository waterMeterRepository;

    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private UsageDataRepository usageRepo;

    @Autowired
    private WaterMeterRepository meterRepo;

    @Transactional
    public void addUserAndWaterMeter(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        WaterMeter meter = new WaterMeter();
        meter.setLocation(dto.getMeterLocation());
        meter.setStatus(dto.getMeterStatus());
        meter.setUser(user); // Set the relationship

        // Save user first to generate ID
        userRepository.save(user);

        // Save meter with user reference
        waterMeterRepository.save(meter);
    }

    @Transactional
    public void updateMeterStatus(MeterStatusUpdateRequestDTO dto) {
        List<WaterMeter> meters = waterMeterRepository.findByUserId(dto.getUserId());
        if (meters.isEmpty()) {
            throw new EntityNotFoundException("No water meter found for user ID: " + dto.getUserId());
        }

        for (WaterMeter meter : meters) {
            meter.setStatus(dto.getNewStatus());
        }

        waterMeterRepository.saveAll(meters);
    }

    public void addWaterUsage(WaterUsageRequestDTO dto) {
        List<WaterMeter> meters = meterRepo.findByUserId(dto.getUserId());
        if (meters.isEmpty()) {
            throw new EntityNotFoundException("No water meter found for user ID: " + dto.getUserId());
        }

        OffsetDateTime timestamp = dto.getTimestamp() != null ? dto.getTimestamp() : OffsetDateTime.now(ZoneOffset.UTC);

        for (WaterMeter meter : meters) {
            UsageData usage = new UsageData();
            usage.setMeter(meter);
            usage.setTimestamp(timestamp);
            usage.setConsumptionLiters(dto.getConsumptionLiters());
            usageRepo.save(usage);

            if (dto.getConsumptionLiters() > 80) {
                Alert alert = new Alert();
                alert.setMeter(meter);
                alert.setType("LEAK");
                alert.setMessage("High consumption detected: " + dto.getConsumptionLiters() + " liters");
                alert.setCreatedAt(timestamp);
                alertRepo.save(alert);
            }
        }
    }


}

