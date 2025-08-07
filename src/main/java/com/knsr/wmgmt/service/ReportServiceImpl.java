package com.knsr.wmgmt.service;

import com.knsr.wmgmt.dto.response.*;
import com.knsr.wmgmt.repository.ReportRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public DashboardSummaryResponseDTO  getDashboardSummary() {
        DashboardSummaryResponseDTO summary = new DashboardSummaryResponseDTO();
        summary.setTotalUsers(reportRepository.countTotalUsers());
        summary.setTotalZones(reportRepository.countTotalZones());
        summary.setActiveMeters(reportRepository.countActiveMeters());
        summary.setTotalConsumption(reportRepository.sumTotalConsumption());
        summary.setPendingBills(reportRepository.countPendingBills());
        return summary;
    }

    @Override
    public List<MonthlyUsageResponseDTO> getMonthlyUsagePerUser() {
        return reportRepository.getMonthlyUsagePerUser().stream().map(row -> {
            MonthlyUsageResponseDTO dto = new MonthlyUsageResponseDTO();
            dto.setUsername((String) row[0]);
            dto.setMonth(formatToYearMonth(row[1]));
            dto.setTotalConsumption((Double) row[2]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ZoneMonthlyUsageResponseDTO> getMonthlyUsagePerZone() {
        return reportRepository.getMonthlyUsagePerZone().stream().map(row -> {
            ZoneMonthlyUsageResponseDTO dto = new ZoneMonthlyUsageResponseDTO();
            dto.setZoneName((String) row[0]);
            dto.setMonth(formatToYearMonth(row[1]));
            dto.setTotalConsumption((Double) row[2]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RecentAlertResponseDTO> getRecentAlerts() {
        return reportRepository.getRecentAlerts().stream().map(row -> {
            RecentAlertResponseDTO dto = new RecentAlertResponseDTO();
            dto.setDate(convertToOffsetDateTime(row[0]));
            dto.setMeterId(((Number) row[1]).longValue());
            dto.setZone((String) row[2]);
            dto.setType((String) row[3]);
            dto.setMessage((String) row[4]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BillingSummaryResponseDTO> getBillingSummary() {
        return reportRepository.getBillingSummary().stream().map(row -> {
            BillingSummaryResponseDTO dto = new BillingSummaryResponseDTO();
            dto.setUsername((String) row[0]);
            dto.setAmount((Double) row[1]);
            dto.setStatus((String) row[2]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AnomalyUsageResponseDTO> getHighUsageAnomalies() {
        return reportRepository.getHighUsageAnomalies().stream().map(row -> {
            AnomalyUsageResponseDTO dto = new AnomalyUsageResponseDTO();
            dto.setUsername((String) row[0]);
            dto.setTimestamp(convertToOffsetDateTime(row[1]));
            dto.setConsumptionLiters((Double) row[2]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EfficiencyScoreResponseDTO> getEfficiencyScores() {
        return reportRepository.getEfficiencyScores().stream().map(row -> {
            EfficiencyScoreResponseDTO dto = new EfficiencyScoreResponseDTO();
            dto.setUsername((String) row[0]);
            Object rawScore = row[1];
            if (rawScore instanceof BigDecimal bd) {
                dto.setEfficiencyScore(bd.doubleValue());
            } else if (rawScore instanceof Double d) {
                dto.setEfficiencyScore(d);
            } else {
                throw new IllegalArgumentException("Unexpected type for efficiency score: " + rawScore.getClass());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ New Methods

    @Override
    public List<RoleMonthlyConsumptionResponseDTO> getMonthlyConsumptionByRole() {
        return reportRepository.getRoleMonthlyConsumption().stream().map(row -> {
            RoleMonthlyConsumptionResponseDTO dto = new RoleMonthlyConsumptionResponseDTO();
            dto.setRole((String) row[0]);
            dto.setMonth(formatToYearMonth(row[1]));
            dto.setTotalConsumptionLiters((Double) row[2]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RealtimeAlertResponseDTO> getRealtimeAlerts() {
        OffsetDateTime thresholdTime = OffsetDateTime.now(ZoneOffset.UTC).minusHours(10);
        return reportRepository.findRecentAlerts(thresholdTime);
    }

    @Override
    public List<UserTypeConsumptionResponseDTO> getConsumptionByUserType() {
        return reportRepository.getConsumptionByUserType();
    }

    @Override
    public List<HighUsageZoneResponseDTO> getHighUsageZones() {
        double threshold = 150.0;
        return reportRepository.getHighUsageZones(threshold);
    }

    // ✅ Utility Methods

    private String formatToYearMonth(Object rawDate) {
        OffsetDateTime offsetDateTime = convertToOffsetDateTime(rawDate);
        return offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    private OffsetDateTime convertToOffsetDateTime(Object rawDate) {
        if (rawDate instanceof Timestamp ts) {
            return ts.toInstant().atOffset(ZoneOffset.UTC);
        } else if (rawDate instanceof Instant instant) {
            return instant.atOffset(ZoneOffset.UTC);
        } else if (rawDate instanceof LocalDateTime ldt) {
            return ldt.atOffset(ZoneOffset.UTC);
        } else if (rawDate instanceof java.sql.Date date) {
            return date.toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
        } else {
            throw new IllegalArgumentException("Unexpected date type: " + rawDate.getClass());
        }
    }
}
