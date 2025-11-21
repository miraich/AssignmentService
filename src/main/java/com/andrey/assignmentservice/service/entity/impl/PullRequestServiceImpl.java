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
    public PullRequest save(PullRequest pullRequest) {
        if (exists(pullRequest.getId())) {
            throw new PullRequestExistsException("Pull request " + pullRequest.getId() + " already exists");
        }
        return pullRequestRepository.save(pullRequest);
    }

    @Override
    public PullRequest find(String pullRequestId) {
        return pullRequestRepository.findById(pullRequestId).orElseThrow(
                () -> new EntityNotFoundException("Pull request " + pullRequestId + " not found")
        );
    }

    @Override
    public List<PullRequest> findAll() {
        return pullRequestRepository.findAll();
    }

    @Override
    public PullRequest update(PullRequest pullRequest, String id) {
        if (!exists(pullRequest.getId())) {
            throw new EntityNotFoundException("Pull request " + pullRequest.getId() + " not found");
        }
        return pullRequestRepository.save(pullRequest);
    }

    @Override
    public boolean exists(String pullRequestId) {
        return pullRequestRepository.existsById(pullRequestId);
    }
}
