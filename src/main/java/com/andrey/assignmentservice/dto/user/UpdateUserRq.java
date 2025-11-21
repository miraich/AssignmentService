package com.andrey.assignmentservice.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record UpdateUserRq(
        @NotEmpty String userId,
        @NotNull Boolean isActive
) {
}
