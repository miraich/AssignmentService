package com.andrey.assignmentservice.dto.team;

import com.andrey.assignmentservice.dto.user.CreateUserRq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Jacksonized
@Builder(toBuilder = true)
public record CreateTeamRq(
        @NotBlank
        @Schema(name = "team_name")
        String teamName,
        @NotEmpty Set<CreateUserRq> members
) {
}
