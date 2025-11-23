package com.andrey.assignmentservice.service.impl;

import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.service.entity.TeamService;
import com.andrey.assignmentservice.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamManagementServiceImpl {
    private final TeamService teamService;
    private final UserService userService;

    @Transactional
    public Team createTeamWithMembers(Team team) {
        Team savedTeam = teamService.create(team);

        for (User user : team.getMembers()) {
            user.setTeam(savedTeam);
            userService.create(user);
        }

        return savedTeam;
    }
}
