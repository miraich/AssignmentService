package com.andrey.assignmentservice.service.impl;

import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestDto;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRq;
import com.andrey.assignmentservice.enums.PullRequestStatus;
import com.andrey.assignmentservice.exception.PullRequestMergedException;
import com.andrey.assignmentservice.exception.PullRequestNoCandidateException;
import com.andrey.assignmentservice.exception.PullRequestNotAssignedException;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.service.entity.PullRequestService;
import com.andrey.assignmentservice.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PullRequestManagementServiceImpl {
    private final PullRequestService pullRequestService;
    private final UserService userService;

    @Transactional
    public PullRequest createPullRequest(PullRequest pullRequest) {
        pullRequest.setStatus(PullRequestStatus.OPEN);
        User author = userService.get(pullRequest.getAuthor().getId());
        pullRequest.setAuthor(author);
        List<User> authorTeammates = pullRequest
                .getAuthor()
                .getTeam()
                .getMembers()
                .stream()
                .filter(teamMember -> !teamMember.getId().equals(author.getId()))
                .filter(User::getIsActive)
                .toList();

        Set<User> randomReviewers = selectRandomReviewers(authorTeammates, 2);
        pullRequest.setAssignedReviewers(randomReviewers);
        return pullRequestService.save(pullRequest);
    }

    public Set<PullRequest> getUserPullRequests(String userId) {
        User user = userService.get(userId);
        return user.getPullRequests();
    }

    @Transactional
    public PullRequest mergePullRequest(String pullRequestId) {
        PullRequest pullRequest = pullRequestService.find(pullRequestId);
        if (pullRequest.getStatus().equals(PullRequestStatus.MERGED)) {
            return pullRequest;
        }
        pullRequest.setMergedAt(Instant.now());
        pullRequest.setStatus(PullRequestStatus.MERGED);
        return pullRequestService.update(pullRequest, pullRequestId);
    }

    @Transactional
    public ReassignPullRequestDto reassignPullRequest(ReassignPullRequestRq reassignPullRequestRq) {
        PullRequest pullRequest = pullRequestService.find(reassignPullRequestRq.pullRequestId());
        if (pullRequest.getStatus().equals(PullRequestStatus.MERGED)) {
            throw new PullRequestMergedException("Merged pull requests cannot be reassigned");
        }

        User oldReviewer = userService.get(reassignPullRequestRq.oldReviewerId());
        Set<User> assignedReviewers = pullRequest.getAssignedReviewers();

        if (!assignedReviewers.contains(oldReviewer)) {
            throw new PullRequestNotAssignedException(String.format("Reviewer %s is not assigned to pull request %s",
                    oldReviewer.getId(), pullRequest.getId()));
        }

        User author = pullRequest.getAuthor();

        List<User> teammates = oldReviewer
                .getTeam()
                .getMembers()
                .stream()
                .filter(teamMember -> !teamMember.getId().equals(author.getId())
                        && !teamMember.getId().equals(oldReviewer.getId())
                        && !assignedReviewers.contains(teamMember)
                )
                .toList();

        if (teammates.stream().noneMatch(User::getIsActive)) {
            throw new PullRequestNoCandidateException("Pull request does not have any replacement active team members");
        }

        User user = teammates.get(ThreadLocalRandom.current().nextInt(teammates.size()));
        pullRequest.getAssignedReviewers().remove(oldReviewer);
        pullRequest.getAssignedReviewers().add(user);

        return new ReassignPullRequestDto(pullRequest, user.getId());
    }

    private Set<User> selectRandomReviewers(List<User> candidates, int maxCount) {
        if (candidates.isEmpty()) {
            return Set.of();
        }

        int count = Math.min(candidates.size(), maxCount);
        List<User> shuffled = new ArrayList<>(candidates);
        Collections.shuffle(shuffled);

        return new HashSet<>(shuffled.subList(0, count));
    }
}
