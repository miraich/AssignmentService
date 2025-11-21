package com.andrey.assignmentservice.service.entity.impl;

import com.andrey.assignmentservice.exception.TeamExistsException;
import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.repository.TeamRepository;
import com.andrey.assignmentservice.service.entity.TeamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        if (exists(team.getTeamName())) {
            throw new TeamExistsException("Team with name " + team.getTeamName() + " already exists");
        }
        return teamRepository.save(team);
    }

    @Override
    public Team findByTeamName(String teamName) {
        if (!exists(teamName)) {
            throw new EntityNotFoundException("Team with name " + teamName + " does not exist");
        }
        return teamRepository.findByTeamName(teamName);
    }

    @Override
    public boolean exists(String teamName) {
        return teamRepository.existsByTeamName(teamName);
    }
}
