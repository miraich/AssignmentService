package com.andrey.assignmentservice.service.entity.impl;

import com.andrey.assignmentservice.exception.PullRequestExistsException;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.repository.PullRequestRepository;
import com.andrey.assignmentservice.service.entity.PullRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PullRequestServiceImpl implements PullRequestService {
    private final PullRequestRepository pullRequestRepository;

    @Override
    public PullRequest create(PullRequest pullRequest) {
        if (exists(pullRequest.getId())) {
            throw new PullRequestExistsException("Pull request " + pullRequest.getId() + " already exists");
        }
        return save(pullRequest);
    }

    @Override
    public PullRequest find(String pullRequestId) {
        return pullRequestRepository.findById(pullRequestId).orElseThrow(
                () -> new EntityNotFoundException("Pull request " + pullRequestId + " not found")
        );
    }

    @Override
    public PullRequest findWithReviewers(String pullRequestId) {
        return pullRequestRepository.findWithReviewers(pullRequestId).orElseThrow(
                () -> new EntityNotFoundException("Pull request " + pullRequestId + " not found")
        );
    }

    @Override
    public PullRequest findWithReviewersAndTheirTeams(String pullRequestId) {
        return pullRequestRepository.findWithReviewersAndTheirTeams(pullRequestId).orElseThrow(
                () -> new EntityNotFoundException("Pull request " + pullRequestId + " not found")
        );
    }

    @Override
    public List<PullRequest> findAllWithReviewersAndTheirTeams() {
        return pullRequestRepository.findAllWithReviewersAndTheirTeams();
    }

    @Override
    public PullRequest save(PullRequest pullRequest) {
        return pullRequestRepository.save(pullRequest);
    }

    @Override
    public boolean exists(String pullRequestId) {
        return pullRequestRepository.existsById(pullRequestId);
    }
}
