package com.hackathon.ibm.controller;

import com.hackathon.ibm.dto.ActivityDataDto;
import com.hackathon.ibm.service.ActivityDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity-data")
@RequiredArgsConstructor
public class ActivityDataController {
    private final ActivityDataService activityDataService;

    @PostMapping
    public ActivityDataDto saveActivityData(@RequestBody ActivityDataDto dto) {
        return activityDataService.saveActivityData(dto);
    }

    @GetMapping("/{userId}")
    public ActivityDataDto getTodayActivityData(@PathVariable Long userId) {
        return activityDataService.getTodayActivityDataForUser(userId);
    }
}
