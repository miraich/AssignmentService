package com.andrey.assignmentservice.dto.pullrequest;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Builder(toBuilder = true)
public record GetUserPullRequestsRs(
        String userId,
        Set<GetUserPullRequestRs> pullRequests
) {
}
