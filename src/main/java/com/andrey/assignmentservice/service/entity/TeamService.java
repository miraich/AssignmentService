package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.Team;

public interface TeamService {
    Team save(Team team);

    Team findByTeamName(String teamName);

    boolean exists(String teamName);
}
