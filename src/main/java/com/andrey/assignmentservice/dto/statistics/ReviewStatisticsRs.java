package com.andrey.assignmentservice.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Jacksonized
@Builder(toBuilder = true)
public record ReviewStatisticsRs(
        @Schema(description = "Общее количество Pull Requests", example = "150")
        Long totalPullRequests,

        @Schema(description = "Количество открытых Pull Requests", example = "25")
        Long openPullRequests,

        @Schema(description = "Количество вмерженных Pull Requests", example = "125")
        Long mergedPullRequests,

        @Schema(description = "Назначения по пользователям",
                example = "{\"user1\": 10, \"user2\": 15, \"user3\": 5}")
        Map<String, Long> assignmentsPerUser,

        @Schema(description = "Количество ревью по каждому Pull Request",
                example = "{\"PR-123\": 3, \"PR-456\": 5, \"PR-789\": 2}")
        Map<String, Long> reviewsPerPullRequest
) {
}
