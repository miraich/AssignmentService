package com.andrey.assignmentservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record UpdateUserRq(
        @NotEmpty
        @Schema(name = "user_id")
        String userId,

        @NotNull
        @Schema(name = "is_active")
        Boolean isActive
) {
}
