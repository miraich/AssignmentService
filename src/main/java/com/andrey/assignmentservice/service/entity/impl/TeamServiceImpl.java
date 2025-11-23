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
    public Team create(Team team) {
        if (exists(team.getTeamName())) {
            throw new TeamExistsException("Team with name " + team.getTeamName() + " already exists");
        }
        return save(team);
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team findByTeamNameWithMembers(String teamName) {
        return teamRepository.findByTeamNameWithMembers(teamName).orElseThrow(
                () -> new EntityNotFoundException("Team with name " + teamName + " does not exist")
        );
    }

    @Override
    public boolean exists(String teamName) {
        return teamRepository.existsByTeamName(teamName);
    }
}
