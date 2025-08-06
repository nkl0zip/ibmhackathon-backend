package com.hackathon.ibm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "activity_data", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class ActivityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private Integer filesAccessed = 0;
    private Double dataTransferMb = 0.0;
    private Integer emailsSent = 0;
    private Boolean usbEvents = false;
    private Integer printJobs = 0;
    private Boolean priviledgeEscalation = false;
    private Boolean vpnConnection = false;
    private Boolean unusualAppUsage = false;
    private Boolean policyViolation = false;
    private Integer externalEmailCount = 0;
    private Boolean securityAlertTrigerred = false;
}
