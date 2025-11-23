package com.andrey.assignmentservice.controller.api;

import com.andrey.assignmentservice.controller.TeamController;
import com.andrey.assignmentservice.dto.team.CreateTeamRq;
import com.andrey.assignmentservice.dto.team.CreateTeamRs;
import com.andrey.assignmentservice.dto.team.GetTeamRs;
import com.andrey.assignmentservice.dto.user.CreateUserRq;
import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.repository.TeamRepository;
import com.andrey.assignmentservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TeamRestControllerIT {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
    }

    @Test
    void handleCreateTeamWithMembers_ReturnsValidResponseEntity() {
        CreateUserRq user1 = CreateUserRq.builder()
                .userId("u1")
                .username("Andrey")
                .isActive(true)
                .build();

        CreateUserRq user2 = CreateUserRq.builder()
                .userId("u2")
                .username("Tom")
                .isActive(true)
                .build();

        CreateUserRq user3 = CreateUserRq.builder()
                .userId("u3")
                .username("John")
                .isActive(true)
                .build();

        CreateUserRq user4 = CreateUserRq.builder()
                .userId("u4")
                .username("Kyle")
                .isActive(true)
                .build();

        CreateUserRq user5 = CreateUserRq.builder()
                .userId("u5")
                .username("Den")
                .isActive(true)
                .build();

        CreateTeamRq teamRq = CreateTeamRq.builder()
                .teamName("payments")
                .members(Set.of(user1, user2, user3, user4, user5))
                .build();

        CreateTeamRs teamRs = teamController.createTeamWithMembers(teamRq);

        Assertions.assertNotNull(teamRs);

        Team finalTeam1 = teamRepository.findByTeamNameWithMembers(teamRs.teamName()).get();

        teamRs.members().forEach(apiUser -> {
            User repoUser = finalTeam1.getMembers().stream()
                    .filter(u -> u.getId().equals(apiUser.userId()))
                    .findFirst()
                    .orElseThrow();

            assertThat(repoUser.getUsername()).isEqualTo(apiUser.username());
            assertThat(repoUser.getId()).isEqualTo(apiUser.userId());
            assertThat(repoUser.getIsActive()).isEqualTo(apiUser.isActive());
        });

    }

    @Test
    void handleGetTeamWithMembers_ReturnsValidResponseEntity() {
        User user1 = User.builder()
                .id("u1")
                .username("Andrey")
                .isActive(true)
                .build();

        User user2 = User.builder()
                .id("u2")
                .username("Tom")
                .isActive(true)
                .build();

        User user3 = User.builder()
                .id("u3")
                .username("John")
                .isActive(true)
                .build();

        User user4 = User.builder()
                .id("u4")
                .username("Kyle")
                .isActive(true)
                .build();

        User user5 = User.builder()
                .id("u5")
                .username("Den")
                .isActive(true)
                .build();

        Team team = Team.builder()
                .teamName("payments")
                .members(Set.of(user1, user2, user3, user4, user5))
                .build();

        Team finalTeam = team;
        team.getMembers().forEach(u -> u.setTeam(finalTeam));
        team = teamRepository.save(team);
        userRepository.saveAll(team.getMembers());

        GetTeamRs teamRs = teamController.getTeam(team.getTeamName());

        Assertions.assertNotNull(teamRs);

        assertThat(teamRs.teamName()).isEqualTo(team.getTeamName());
        assertThat(teamRs.members().size()).isEqualTo(team.getMembers().size());

        Team finalTeam1 = team;
        teamRs.members().forEach(responseUser -> {
            User repoUser = finalTeam1.getMembers().stream()
                    .filter(u -> u.getId().equals(responseUser.userId()))
                    .findFirst()
                    .orElseThrow();

            assertThat(repoUser.getUsername()).isEqualTo(responseUser.username());
            assertThat(repoUser.getId()).isEqualTo(responseUser.userId());
            assertThat(repoUser.getIsActive()).isEqualTo(responseUser.isActive());
        });
    }
}
