package com.hackathon.ibm.service;

import com.hackathon.ibm.dto.ActivityDataDto;
import com.hackathon.ibm.model.ActivityData;
import com.hackathon.ibm.model.User;
import com.hackathon.ibm.repository.ActivityDataRepository;
import com.hackathon.ibm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ActivityDataService {
    private final ActivityDataRepository activityDataRepository;
    private final UserRepository userRepository;

    // Add or update today's activity data for user
    @Transactional
    public ActivityDataDto saveActivityData(ActivityDataDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = dto.getDate() != null ? dto.getDate() : LocalDate.now();

        ActivityData data = activityDataRepository.findByUserAndDate(user, today)
                .orElse(ActivityData.builder()
                        .user(user)
                        .date(today)
                        .build());

        data.setFilesAccessed(dto.getFilesAccessed());
        data.setDataTransferMb(dto.getDataTransferMb());
        data.setEmailsSent(dto.getEmailsSent());
        data.setUsbEvents(dto.getUsbEvents());
        data.setPrintJobs(dto.getPrintJobs());
        data.setPriviledgeEscalation(dto.getPriviledgeEscalation());
        data.setVpnConnection(dto.getVpnConnection());
        data.setUnusualAppUsage(dto.getUnusualAppUsage());
        data.setPolicyViolation(dto.getPolicyViolation());
        data.setExternalEmailCount(dto.getExternalEmailCount());
        data.setSecurityAlertTrigerred(dto.getSecurityAlertTrigerred());

        data = activityDataRepository.save(data);

        return ActivityDataDto.builder()
                .id(data.getId())
                .userId(user.getId())
                .date(data.getDate())
                .filesAccessed(data.getFilesAccessed())
                .dataTransferMb(data.getDataTransferMb())
                .emailsSent(data.getEmailsSent())
                .usbEvents(data.getUsbEvents())
                .printJobs(data.getPrintJobs())
                .priviledgeEscalation(data.getPriviledgeEscalation())
                .vpnConnection(data.getVpnConnection())
                .unusualAppUsage(data.getUnusualAppUsage())
                .policyViolation(data.getPolicyViolation())
                .externalEmailCount(data.getExternalEmailCount())
                .securityAlertTrigerred(data.getSecurityAlertTrigerred())
                .build();
    }

    // Fetch today's activity data for a user
    public ActivityDataDto getTodayActivityDataForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate today = LocalDate.now();
        ActivityData data = activityDataRepository.findByUserAndDate(user, today)
                .orElse(null);
        if (data == null) return null;
        return ActivityDataDto.builder()
                .id(data.getId())
                .userId(user.getId())
                .date(data.getDate())
                .filesAccessed(data.getFilesAccessed())
                .dataTransferMb(data.getDataTransferMb())
                .emailsSent(data.getEmailsSent())
                .usbEvents(data.getUsbEvents())
                .printJobs(data.getPrintJobs())
                .priviledgeEscalation(data.getPriviledgeEscalation())
                .vpnConnection(data.getVpnConnection())
                .unusualAppUsage(data.getUnusualAppUsage())
                .policyViolation(data.getPolicyViolation())
                .externalEmailCount(data.getExternalEmailCount())
                .securityAlertTrigerred(data.getSecurityAlertTrigerred())
                .build();
    }
}
