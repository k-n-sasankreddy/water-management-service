package com.knsr.wmgmt.service;

import com.knsr.wmgmt.dto.request.BillingPaymentRequestDTO;
import com.knsr.wmgmt.dto.response.BillingResponseDTO;
import com.knsr.wmgmt.entity.Billing;
import com.knsr.wmgmt.entity.User;
import com.knsr.wmgmt.repository.BillingRepository;
import com.knsr.wmgmt.repository.UsageRepository;
import com.knsr.wmgmt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private UserRepository userRepository;


    private static final double PRICE_PER_LITER = 10;

    @Override
    public List<BillingResponseDTO> generateBillsForAllUsers(OffsetDateTime periodStart, OffsetDateTime periodEnd) {
        List<Long> userIds = usageRepository.findDistinctUserIdsWithUsage(periodStart, periodEnd);
        List<BillingResponseDTO> bills = new ArrayList<>();

        for (Long userId : userIds) {
            boolean hasPaidBill = billingRepository.existsByUserIdAndPeriodStartAndPeriodEndAndStatus(
                    userId, periodStart, periodEnd, "PAID"
            );

            if (!hasPaidBill) {
                // Delete any existing non-paid bills for this period
                List<Billing> existingBills = billingRepository.findByUserIdAndPeriodStartAndPeriodEnd(userId, periodStart, periodEnd);
                for (Billing bill : existingBills) {
                    billingRepository.delete(bill);
                }

                // Regenerate bill
                bills.add(generateBillForUser(userId, periodStart, periodEnd));
            }
        }

        return bills;
    }

    @Override
    public BillingResponseDTO generateBillForUser(Long userId, OffsetDateTime periodStart, OffsetDateTime periodEnd) {
        double totalConsumption = usageRepository.getTotalConsumptionByUser(userId, periodStart, periodEnd);
        double amount = totalConsumption * PRICE_PER_LITER;

        // ✅ Fetch the User entity using userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Billing bill = new Billing();
        bill.setUser(user); // ✅ Set the User object
        bill.setPeriodStart(periodStart);
        bill.setPeriodEnd(periodEnd);
        bill.setAmount(amount);
        bill.setStatus("PENDING");
        bill.setCreatedAt(OffsetDateTime.now());
        bill.setUpdatedAt(OffsetDateTime.now());

        billingRepository.save(bill);

        return mapToDTO(bill);
    }


    @Override
    public BillingResponseDTO updateBillingStatus(BillingPaymentRequestDTO dto) {
        Billing bill = billingRepository.findTopByUserIdAndStatusInOrderByPeriodEndDesc(
                dto.getUserId(), List.of("PENDING", "UNPAID")
        ).orElseThrow(() -> new RuntimeException("No unpaid or pending bill found for user"));

        bill.setStatus("PAID");
        bill.setUpdatedAt(OffsetDateTime.now());
        billingRepository.save(bill);

        return mapToDTO(bill);
    }


    @Override
    public List<BillingResponseDTO> getBillsForUser(Long userId) {
        List<Billing> bills = billingRepository.findByUserId(userId);
        return bills.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private BillingResponseDTO mapToDTO(Billing bill) {
        BillingResponseDTO dto = new BillingResponseDTO();
        dto.setId(bill.getId());
        dto.setUserId(bill.getUser().getId());
        dto.setPeriodStart(bill.getPeriodStart());
        dto.setPeriodEnd(bill.getPeriodEnd());
        dto.setAmount(bill.getAmount());
        dto.setStatus(bill.getStatus());
        dto.setCreatedAt(bill.getCreatedAt());
        return dto;
    }
}