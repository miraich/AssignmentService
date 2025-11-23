package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.PullRequest;

import java.util.List;

public interface PullRequestService {
    PullRequest create(PullRequest pullRequest);

    PullRequest find(String id);

    PullRequest findWithReviewers(String pullRequestId);

    PullRequest findWithReviewersAndTheirTeams(String pullRequestId);

    List<PullRequest> findAllWithReviewersAndTheirTeams();

    PullRequest save(PullRequest pullRequest);

    boolean exists(String pullRequestId);
}
