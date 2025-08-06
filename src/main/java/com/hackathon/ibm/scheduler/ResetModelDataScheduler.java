package com.hackathon.ibm.scheduler;

import com.hackathon.ibm.service.ModelDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetModelDataScheduler {
    private final ModelDataService modelDataService;

    // Every Monday at 00:01
    @Scheduled(cron = "0 1 0 * * MON")
    public void resetDayOfWeek() {
        modelDataService.resetDayOfWeekOnMonday();
    }
}
