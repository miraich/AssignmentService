package com.andrey.assignmentservice.dto.pullrequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record MergePullRequestRq(
        @NotBlank
        String pullRequestId
) {
}
