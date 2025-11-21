package com.andrey.assignmentservice.dto.error;

import com.andrey.assignmentservice.enums.ErrorCode;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record ErrorRs(
        String message,
        ErrorCode code
) {
}
