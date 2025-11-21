package com.andrey.assignmentservice.dto.pullrequest;

import com.andrey.assignmentservice.enums.PullRequestStatus;

import java.util.Set;

public record ReassignPullRequestRs(
        String pullRequestId,
        String pullRequestName,
        String authorId,
        PullRequestStatus status,
        Set<String> assignedReviewers,
        String replacedBy
) {
}
