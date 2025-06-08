package com.dsce.wmgmt.service;


import com.dsce.wmgmt.entity.Billing;
import com.dsce.wmgmt.entity.User;
import com.dsce.wmgmt.entity.WaterMeter;
import com.dsce.wmgmt.repository.BillingRepository;
import com.dsce.wmgmt.repository.UserRepository;
import com.dsce.wmgmt.repository.WaterMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.time.LocalDate;


@Service
public class LoadService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WaterMeterRepository meterRepo;

    @Autowired
    private BillingRepository billingRepo;

    /**
     * One-time load of users and water meters.
     * @param userCount number of users to generate
     */
    public void loadInitialData(int userCount) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@example.com");
            user.setRole("consumer");
            user.setKeycloakId(UUID.randomUUID().toString());
            users.add(user);
        }
        userRepo.saveAll(users);

        List<WaterMeter> meters = new ArrayList<>();
        for (User user : users) {
            WaterMeter meter = new WaterMeter();
            meter.setLocation("Zone-" + new Random().nextInt(100));
            meter.setUserId(user.getId());
            meter.setStatus("active");
            meters.add(meter);
        }
        meterRepo.saveAll(meters);
    }

    /**
     * Generates initial billing records for all users.
     */
    public void generateInitialBilling() {
        List<User> users = userRepo.findAll();
        for (User user : users) {
            Billing bill = new Billing();
            bill.setUserId(user.getId()); // Set the user object directly
            bill.setPeriodStart(LocalDate.now().minusMonths(1));
            bill.setPeriodEnd(LocalDate.now());
            bill.setAmount(500 + new Random().nextDouble() * 1000); // ₹500–₹1500
            bill.setStatus("unpaid");
            billingRepo.save(bill);
        }
    }
}


