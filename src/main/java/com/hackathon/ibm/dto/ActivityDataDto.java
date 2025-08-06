package com.hackathon.ibm.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDataDto {
    private Long id;
    private Long userId;
    private LocalDate date;
    private Integer filesAccessed;
    private Double dataTransferMb;
    private Integer emailsSent;
    private Boolean usbEvents;
    private Integer printJobs;
    private Boolean priviledgeEscalation;
    private Boolean vpnConnection;
    private Boolean unusualAppUsage;
    private Boolean policyViolation;
    private Integer externalEmailCount;
    private Boolean securityAlertTrigerred;
}
