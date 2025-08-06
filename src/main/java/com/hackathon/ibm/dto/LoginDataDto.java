package com.hackathon.ibm.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDataDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private LocalDate date;
    private Integer loginHours;
    private Integer failedLoginAttempts;
}
