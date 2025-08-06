package com.hackathon.ibm.dto;

import com.hackathon.ibm.model.AccessLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String department;
    private AccessLevel accessLevel = AccessLevel.LOW;
    private Integer performance = 0;
    private Integer salaryBand = 1;
    private Integer tenureMonths = 0;
}
