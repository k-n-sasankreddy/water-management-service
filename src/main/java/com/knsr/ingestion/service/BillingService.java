package com.knsr.ingestion.service;

import com.knsr.ingestion.dto.request.BillingPaymentRequestDTO;
import com.knsr.ingestion.dto.request.BillingRequestDTO;
import com.knsr.ingestion.dto.response.BillingResponseDTO;
import com.knsr.ingestion.entity.Billing;
import com.knsr.ingestion.entity.UsageData;
import com.knsr.ingestion.entity.User;
import com.knsr.ingestion.entity.WaterMeter;
import com.knsr.ingestion.repository.BillingRepository;
import com.knsr.ingestion.repository.UsageDataRepository;
import com.knsr.ingestion.repository.UserRepository;
import com.knsr.ingestion.repository.WaterMeterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepo;
    private final UserRepository userRepo;
    private final UsageDataRepository usageRepo;
    private final WaterMeterRepository waterMeterRepo;

    private static final double COST_PER_LITER = 5.0;

    // ✅ Scheduled Run Once Every Month (e.g., on the 1st at midnight)
    //@Scheduled(cron = "0 0 0 1 * *")
    public BillingResponseDTO generateBill(BillingRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //List<WaterMeter> meters = waterMeterRepo.findByUser(user);
        List<WaterMeter> meters = waterMeterRepo.findByUserId(dto.getUserId());

        double totalConsumption = meters.stream()
                .flatMap(meter -> usageRepo.findByMeterAndTimestampBetween(
                        meter, dto.getPeriodStart(), dto.getPeriodEnd()).stream())
                .mapToDouble(UsageData::getConsumptionLiters)
                .sum();


        double amount = totalConsumption * COST_PER_LITER;

        Billing bill = new Billing();
        bill.setUser(user);
        bill.setPeriodStart(dto.getPeriodStart());
        bill.setPeriodEnd(dto.getPeriodEnd());
        bill.setAmount(amount);
        bill.setStatus("PENDING");

        billingRepo.save(bill);

        return mapToResponseDTO(bill);
    }

    public BillingResponseDTO updateBillingStatus(BillingPaymentRequestDTO dto) {
        Billing bill = billingRepo.findById(dto.getBillingId())
                .orElseThrow(() -> new EntityNotFoundException("Billing record not found"));

        bill.setStatus(dto.getStatus());
        billingRepo.save(bill);

        return mapToResponseDTO(bill);
    }

    private BillingResponseDTO mapToResponseDTO(Billing bill) {
        BillingResponseDTO dto = new BillingResponseDTO();
        dto.setBillingId(bill.getId());
        dto.setUserId(bill.getUser().getId());
        dto.setPeriodStart(bill.getPeriodStart());
        dto.setPeriodEnd(bill.getPeriodEnd());
        dto.setAmount(bill.getAmount());
        dto.setStatus(bill.getStatus());
        dto.setCreatedAt(bill.getCreatedAt());
        return dto;
    }
}