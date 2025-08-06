package com.hackathon.ibm.controller;

import com.hackathon.ibm.dto.LoginDataDto;
import com.hackathon.ibm.model.LoginData;
import com.hackathon.ibm.repository.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login-data")
@RequiredArgsConstructor
public class LoginDataController {
    private final LoginDataRepository loginDataRepository;

    @GetMapping
    public List<LoginDataDto> getAllLoginData() {
        return loginDataRepository.findAll().stream().map(data -> LoginDataDto.builder()
                .id(data.getId())
                .userId(data.getUser().getId())
                .userEmail(data.getUser().getEmail())
                .date(data.getDate())
                .loginHours(data.getLoginHours())
                .failedLoginAttempts(data.getFailedLoginAttempts())
                .build()
        ).collect(Collectors.toList());
    }
}
