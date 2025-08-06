package com.hackathon.ibm.scheduler;

import com.hackathon.ibm.service.LoginDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetLoginDataScheduler {
    private final LoginDataService loginDataService;

    // Reset daily login hours at midnight
    @Scheduled(cron = "0 0 0 * * ?") // Every day at 00:00
    public void resetDailyLoginHours() {
        loginDataService.resetDailyLoginHours();
    }

    // Reset failed login attempts at start of every month
    @Scheduled(cron = "0 0 0 1 * ?") // First day of month at 00:00
    public void resetMonthlyFailedLoginAttempts() {
        loginDataService.resetMonthlyFailedLoginAttempts();
    }
}
