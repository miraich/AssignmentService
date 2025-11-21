package com.andrey.assignmentservice.mapper;

import com.andrey.assignmentservice.config.MapstructConfig;
import com.andrey.assignmentservice.dto.team.CreateTeamRq;
import com.andrey.assignmentservice.dto.team.CreateTeamRs;
import com.andrey.assignmentservice.dto.team.GetTeamRs;
import com.andrey.assignmentservice.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = {UserMapper.class})
public interface TeamMapper {
    @Mapping(target = "id", ignore = true)
    Team map(CreateTeamRq createTeamRq);

    CreateTeamRs mapToCreateRs(Team team);

    GetTeamRs mapToGetRs(Team team);
}
