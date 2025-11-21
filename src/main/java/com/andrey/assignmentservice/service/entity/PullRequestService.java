package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.PullRequest;

import java.util.List;

public interface PullRequestService {
    PullRequest save(PullRequest pullRequest);

    PullRequest find(String id);

    List<PullRequest> findAll();

    PullRequest update(PullRequest pullRequest, String id);

    boolean exists(String pullRequestId);
}
