package com.andrey.assignmentservice.dto.pullrequest;

import com.andrey.assignmentservice.model.PullRequest;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record ReassignPullRequestDto(
        PullRequest pullRequest,
        String replacedBy
) {
}
