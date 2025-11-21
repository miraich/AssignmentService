package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.statistics.ReviewStatisticsRs;
import com.andrey.assignmentservice.service.impl.StatisticsManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsManagementServiceImpl statisticsManagementService;

    @GetMapping("/reviews")
    public ReviewStatisticsRs getReviewStatistics() {
        return statisticsManagementService.getReviewStatistics();
    }
}
