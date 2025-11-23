package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.statistics.ReviewStatisticsRs;
import com.andrey.assignmentservice.service.impl.StatisticsManagementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistics", description = "Статистика")
@RestController
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsManagementServiceImpl statisticsManagementService;

    @Operation(summary = "Получить статистику по ревью", description = "Получить статистику по ревью Pull Request'ов")
    @ApiResponse(responseCode = "200", description = "Статистика по ревью",
            content = @Content(schema = @Schema(implementation = ReviewStatisticsRs.class)))
    @GetMapping("/reviews")
    public ReviewStatisticsRs getReviewStatistics() {
        return statisticsManagementService.getReviewStatistics();
    }
}
