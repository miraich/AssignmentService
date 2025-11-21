package com.andrey.assignmentservice.dto.pullrequest;

import com.andrey.assignmentservice.enums.PullRequestStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record GetUserPullRequestRs(
        String pullRequestId,
        String pullRequestName,
        String authorId,
        PullRequestStatus status
) {
}
