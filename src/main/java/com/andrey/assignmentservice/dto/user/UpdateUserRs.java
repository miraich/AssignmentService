package com.andrey.assignmentservice.dto.user;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record UpdateUserRs(
        String userId,
        String username,
        String teamName,
        Boolean isActive
) {
}
