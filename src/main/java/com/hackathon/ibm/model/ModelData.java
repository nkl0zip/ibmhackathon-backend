package com.hackathon.ibm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "model_data")
public class ModelData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer dayOfWeek; // Number of days logged in this week

    private Boolean isWeekend; // 0 or 1

    private Boolean isOffHours; // 0 or 1

    private String action; // Download, Upload, etc

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private String location; // Remote, Office_A, etc

    private String sourceIp; // IP address

    private LocalDateTime sessionStart; // for duration calc
    private LocalDateTime sessionEnd;   // for duration calc

    private Integer sessionDuration;
}
