package com.andrey.assignmentservice.dto.pullrequest;

import com.andrey.assignmentservice.enums.PullRequestStatus;

import java.time.Instant;
import java.util.Set;

public record MergePullRequestRs(
        String pullRequestId,
        String pullRequestName,
        String authorId,
        PullRequestStatus status,
        Set<String> assignedReviewers,
        Instant mergedAt
) {
}
