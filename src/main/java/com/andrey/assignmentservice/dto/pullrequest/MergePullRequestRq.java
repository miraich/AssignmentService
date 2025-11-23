package com.andrey.assignmentservice.dto.pullrequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record MergePullRequestRq(
        @NotBlank
        @Schema(name = "pull_request_id")
        String pullRequestId
) {
}
