package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.Team;

public interface TeamService {
    Team create(Team team);

    Team save(Team team);

    Team findByTeamNameWithMembers(String teamName);

    boolean exists(String teamName);
}
