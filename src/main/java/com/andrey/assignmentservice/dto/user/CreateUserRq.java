package com.andrey.assignmentservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record CreateUserRq(
        @NotBlank
        @Schema(description = "ID пользователя", example = "u1", name = "user_id")
        String userId,

        @NotBlank
        @Schema(description = "Имя пользователя", example = "Alice")
        String username,

        @Schema(description = "Флаг активности", example = "true", name = "is_active")
        Boolean isActive
) {
}
