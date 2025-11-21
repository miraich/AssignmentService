package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestRepository extends JpaRepository<PullRequest, String> {
}
