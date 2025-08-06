package com.hackathon.ibm.service;

import com.hackathon.ibm.model.LoginData;
import com.hackathon.ibm.model.User;
import com.hackathon.ibm.repository.LoginDataRepository;
import com.hackathon.ibm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginDataService {
    private final LoginDataRepository loginDataRepository;
    private final UserRepository userRepository;

    // Get or create today's LoginData row for this user
    @Transactional
    public LoginData getOrCreateTodayData(User user) {
        LocalDate today = LocalDate.now();
        return loginDataRepository.findByUserAndDate(user, today)
                .orElseGet(() -> {
                    LoginData loginData = LoginData.builder()
                            .user(user)
                            .date(today)
                            .loginHours(0)
                            .failedLoginAttempts(0)
                            .build();
                    return loginDataRepository.save(loginData);
                });
    }

    // Add login hours for a user for today
    @Transactional
    public void addLoginHours(User user, int hours) {
        LoginData data = getOrCreateTodayData(user);
        data.setLoginHours(data.getLoginHours() + hours);
        loginDataRepository.save(data);
    }

    // Increase failed login attempts for this user (reset monthly)
    @Transactional
    public void incrementFailedLoginAttempts(User user) {
        LoginData data = getOrCreateTodayData(user);
        data.setFailedLoginAttempts(data.getFailedLoginAttempts() + 1);
        loginDataRepository.save(data);
    }

    // Reset login hours for all users for today (midnight job)
    @Transactional
    public void resetDailyLoginHours() {
        LocalDate today = LocalDate.now();
        for (LoginData data : loginDataRepository.findByDate(today)) {
            data.setLoginHours(0);
            loginDataRepository.save(data);
        }
    }

    // Reset failed login attempts for all users at start of new month
    @Transactional
    public void resetMonthlyFailedLoginAttempts() {
        for (LoginData data : loginDataRepository.findAll()) {
            data.setFailedLoginAttempts(0);
            loginDataRepository.save(data);
        }
    }
}
