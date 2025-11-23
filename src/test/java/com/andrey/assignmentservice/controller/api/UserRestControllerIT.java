package com.andrey.assignmentservice.controller.api;

import com.andrey.assignmentservice.controller.PullRequestController;
import com.andrey.assignmentservice.controller.UserController;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.GetUserPullRequestsRs;
import com.andrey.assignmentservice.dto.user.UpdateUserRq;
import com.andrey.assignmentservice.dto.user.UpdateUserRs;
import com.andrey.assignmentservice.enums.PullRequestStatus;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.repository.PullRequestRepository;
import com.andrey.assignmentservice.repository.TeamRepository;
import com.andrey.assignmentservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRestControllerIT {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private PullRequestController pullRequestController;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @AfterEach
    void setUp() {
        pullRequestRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
    }

    @Test
    void handleGetUserPullRequests_ReturnsValidResponseEntity() {
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

        Team team = Team.builder()
                .teamName("payments")
                .members(Set.of(user1, user2, user3))
                .build();

        Team finalTeam = team;
        team.getMembers().forEach(u -> u.setTeam(finalTeam));
        team = teamRepository.save(team);
        User repoUserAuthor = userRepository.findWithTeam(user1.getId()).get();
        User repoUserAssigned1 = userRepository.findWithTeam(user2.getId()).get();
        User repoUserAssigned2 = userRepository.findWithTeam(user3.getId()).get();

        PullRequest pullRequest = PullRequest.builder()
                .id("pr-1001")
                .name("Add search")
                .author(repoUserAuthor)
                .status(PullRequestStatus.OPEN)
                .assignedReviewers(Set.of(repoUserAssigned1, repoUserAssigned2))
                .build();

        repoUserAssigned1.setPullRequests(new HashSet<>());
        repoUserAssigned2.setPullRequests(new HashSet<>());
        repoUserAssigned1.getPullRequests().add(pullRequest);
        repoUserAssigned2.getPullRequests().add(pullRequest);

        pullRequest = pullRequestRepository.save(pullRequest);
        GetUserPullRequestsRs userPullRequestsRs = userController.getUserPullRequests(user2.getId());

        Assertions.assertNotNull(userPullRequestsRs);
        Assertions.assertNotNull(userPullRequestsRs.pullRequests());

        assertThat(repoUserAssigned1.getId()).isEqualTo(userPullRequestsRs.userId());
        assertThat(repoUserAssigned1.getPullRequests().size()).isEqualTo(userPullRequestsRs.pullRequests().size());

        for (var pr : repoUserAssigned1.getPullRequests()) {
            for (var rsPr : userPullRequestsRs.pullRequests()) {
                assertThat(pr.getId()).isEqualTo(rsPr.pullRequestId());
                assertThat(pr.getName()).isEqualTo(rsPr.pullRequestName());
                assertThat(pr.getAuthor().getId()).isEqualTo(rsPr.authorId());
                assertThat(pr.getStatus()).isEqualTo(rsPr.status());
            }
        }
    }

    @Test
    void handleChangeUserActivity_ReturnsValidResponseEntity() {
        User user = User.builder()
                .id("u1")
                .username("Andrey")
                .isActive(true)
                .build();

        Team team = Team.builder()
                .teamName("payments")
                .members(Set.of(user))
                .build();

        team.getMembers().forEach(u -> u.setTeam(team));
        teamRepository.save(team);

        UpdateUserRq userRq = UpdateUserRq.builder()
                .userId(user.getId())
                .isActive(false)
                .build();

        UpdateUserRs userRs = userController.setUserIsActive(userRq);

        User repoUser = userRepository.findWithTeam(user.getId()).get();

        Assertions.assertNotNull(userRs);

        assertThat(userRs.isActive()).isEqualTo(repoUser.getIsActive());
        assertThat(userRs.userId()).isEqualTo(repoUser.getId());
        assertThat(userRs.username()).isEqualTo(repoUser.getUsername());
        assertThat(userRs.teamName()).isEqualTo(repoUser.getTeam().getTeamName());
    }
}
