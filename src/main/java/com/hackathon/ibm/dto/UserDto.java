package com.hackathon.ibm.dto;

import com.hackathon.ibm.model.Role;
import lombok.*;
import com.hackathon.ibm.model.AccessLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String employeeId;
    private String name;
    private String email;
    private Role role;
    private String department;
    private AccessLevel accessLevel;
    private Integer performance;
    private Integer salaryBand;
    private Integer tenureMonths;
}
