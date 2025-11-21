package com.andrey.assignmentservice.dto.pullrequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record CreatePullRequestRq(
        @NotBlank
        String pullRequestId,
        @NotBlank
        String pullRequestName,
        @NotBlank
        String authorId
) {
}
