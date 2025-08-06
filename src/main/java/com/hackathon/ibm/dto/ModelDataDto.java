package com.hackathon.ibm.dto;

import com.hackathon.ibm.model.DeviceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelDataDto {
    private Long id;
    private Long userId;
    private Integer dayOfWeek;
    private Boolean isWeekend;
    private Boolean isOffHours;
    private String action;
    private DeviceType deviceType;
    private String location;
    private String sourceIp;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
    private Integer sessionDuration;
}
