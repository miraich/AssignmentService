package com.andrey.assignmentservice.dto.team;

import com.andrey.assignmentservice.dto.user.CreateUserRq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Builder(toBuilder = true)
public record CreateTeamRq(
        @NotBlank String teamName,
        @NotEmpty Set<CreateUserRq> members
) {
}
