package com.andrey.assignmentservice.dto.team;

import com.andrey.assignmentservice.dto.user.CreateUserRs;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder(toBuilder = true)
public record GetTeamRs(
        String teamName,
        List<CreateUserRs> members
) {
}
