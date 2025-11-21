package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByTeamName(String name);

    Team findByTeamName(String teamName);
}
