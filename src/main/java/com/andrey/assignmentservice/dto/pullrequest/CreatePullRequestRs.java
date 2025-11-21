package com.andrey.assignmentservice.dto.pullrequest;

import com.andrey.assignmentservice.enums.PullRequestStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Builder(toBuilder = true)
public record CreatePullRequestRs(
        String pullRequestId,
        String pullRequestName,
        String authorId,
        PullRequestStatus status,
        Set<String> assignedReviewers
) {
}
