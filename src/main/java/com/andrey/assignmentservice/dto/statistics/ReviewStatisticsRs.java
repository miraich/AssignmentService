package com.andrey.assignmentservice.dto.statistics;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Jacksonized
@Builder(toBuilder = true)
public record ReviewStatisticsRs(
        Long totalPullRequests,
        Long openPullRequests,
        Long mergedPullRequests,
        Map<String, Long> assignmentsPerUser,
        Map<String, Long> reviewsPerPullRequest
) {
}
