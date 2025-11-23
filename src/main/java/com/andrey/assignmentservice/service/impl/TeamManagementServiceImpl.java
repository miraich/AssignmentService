package com.andrey.assignmentservice.service.impl;

import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.service.entity.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamManagementServiceImpl {
    private final TeamService teamService;

    @Transactional
    public Team createTeamWithMembers(Team team) {
        team.getMembers().forEach(user -> user.setTeam(team));
        return teamService.create(team);
    }
}
