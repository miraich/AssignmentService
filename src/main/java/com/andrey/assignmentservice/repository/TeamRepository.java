package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByTeamName(String name);

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.members WHERE t.teamName = :teamName")
    Optional<Team> findByTeamNameWithMembers(@Param("teamName") String teamName);
}
