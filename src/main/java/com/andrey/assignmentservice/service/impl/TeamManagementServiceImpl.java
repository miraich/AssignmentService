package com.andrey.assignmentservice.service.impl;

import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.service.entity.impl.TeamServiceImpl;
import com.andrey.assignmentservice.service.entity.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamManagementServiceImpl {
    private final TeamServiceImpl teamService;
    private final UserServiceImpl userService;

    @Transactional
    public Team createTeamWithMembers(Team team) {
        Team savedTeam = teamService.save(team);

        for (User user : team.getMembers()) {
            user.setTeam(savedTeam);
            userService.save(user);
        }

        return savedTeam;
    }
}
