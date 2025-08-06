package com.hackathon.ibm.controller;

import com.hackathon.ibm.dto.ModelDataDto;
import com.hackathon.ibm.model.DeviceType;
import com.hackathon.ibm.service.ModelDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/model-data")
@RequiredArgsConstructor
public class ModelDataController {
    private final ModelDataService modelDataService;

    // Add activity/session record for a user
    @PostMapping
    public ModelDataDto addModelData(@RequestBody ModelDataDto dto) {
        return modelDataService.addModelData(
                dto.getUserId(),
                dto.getSessionStart(),
                dto.getSessionEnd(),
                dto.getAction(),
                dto.getDeviceType(),
                dto.getLocation(),
                dto.getSourceIp()
        );
    }

    // Fetch all activity/session data for a user
    @GetMapping("/{userId}")
    public List<ModelDataDto> getDataForUser(@PathVariable Long userId) {
        return modelDataService.getModelDataForUser(userId);
    }
}
