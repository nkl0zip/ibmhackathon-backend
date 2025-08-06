package com.hackathon.ibm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String name;
    private String email;
    private String role;
}
