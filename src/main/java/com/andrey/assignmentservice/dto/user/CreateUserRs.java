package com.andrey.assignmentservice.dto.user;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record CreateUserRs(
        String userId,
        String username,
        Boolean isActive
) {
}
