package com.andrey.assignmentservice.controller.api;

import com.andrey.assignmentservice.controller.PullRequestController;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRs;
import com.andrey.assignmentservice.enums.PullRequestStatus;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.model.Team;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.repository.PullRequestRepository;
import com.andrey.assignmentservice.repository.TeamRepository;
import com.andrey.assignmentservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class PullRequestRestControllerIT {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private PullRequestController pullRequestController;

    @BeforeEach
    void setUp() {
        pullRequestRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();
    }

    @Test
    void handleCreatePullRequestAndAssignReviewers_ReturnsValidResponseEntity() {
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
        userRepository.saveAll(team.getMembers());

        User repoUserAuthor = userRepository.findWithTeam(user1.getId()).get();

        CreatePullRequestRq createPullRequestRq = CreatePullRequestRq.builder()
                .pullRequestId("pr-1001")
                .pullRequestName("Add search")
                .authorId(repoUserAuthor.getId())
                .build();

        CreatePullRequestRs responsePr = pullRequestController.createPullRequest(createPullRequestRq);

        PullRequest repoPr = pullRequestRepository.findWithReviewers(responsePr.pullRequestId()).get();

        Assertions.assertNotNull(responsePr);
        Assertions.assertNotNull(responsePr.assignedReviewers());

        assertThat(repoPr.getId()).isEqualTo(responsePr.pullRequestId());
        assertThat(repoPr.getName()).isEqualTo(responsePr.pullRequestName());
        assertThat(repoPr.getAuthor().getId()).isEqualTo(responsePr.authorId());
        assertThat(repoPr.getStatus()).isEqualTo(responsePr.status());

        Team finalTeam1 = team;
        repoPr.getAssignedReviewers().forEach(reviewer -> {
            User repoUser = finalTeam1.getMembers().stream()
                    .filter(u -> u.getId().equals(reviewer.getId()) && !u.getId().equals(repoUserAuthor.getId()))
                    .findFirst()
                    .orElseThrow();
            assertThat(responsePr.assignedReviewers().contains(repoUser.getId()));
        });
    }

    @Test
    void handleMergePullRequests_ReturnsValidResponseEntity() {
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
        userRepository.saveAll(team.getMembers());
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

        MergePullRequestRs responsePr = pullRequestController.mergePullRequest(
                new MergePullRequestRq("pr-1001")
        );

        Assertions.assertNotNull(responsePr);
        Assertions.assertNotNull(responsePr.assignedReviewers());

        assertThat(pullRequest.getId()).isEqualTo(responsePr.pullRequestId());
        assertThat(pullRequest.getName()).isEqualTo(responsePr.pullRequestName());
        assertThat(pullRequest.getAuthor().getId()).isEqualTo(responsePr.authorId());
        assertThat(pullRequest.getStatus()).isEqualTo(responsePr.status());

        Team finalTeam1 = team;
        pullRequest.getAssignedReviewers().forEach(reviewer -> {
            User repoUser = finalTeam1.getMembers().stream()
                    .filter(u -> u.getId().equals(reviewer.getId()) && !u.getId().equals(repoUserAuthor.getId()))
                    .findFirst()
                    .orElseThrow();
            assertThat(responsePr.assignedReviewers().contains(repoUser.getId()));
        });

    }
}
