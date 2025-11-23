package com.andrey.assignmentservice.repository;

import com.andrey.assignmentservice.model.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PullRequestRepository extends JpaRepository<PullRequest, String> {
    @Query("""
                        SELECT p FROM PullRequest p
                        LEFT JOIN FETCH p.assignedReviewers ar
                        LEFT JOIN FETCH p.author author
                        where p.id = :pullRequestId
            """)
    Optional<PullRequest> findWithReviewers(@Param("pullRequestId") String pullRequestId);

    @Query("""
                SELECT p FROM PullRequest p
                LEFT JOIN FETCH p.assignedReviewers ar
                LEFT JOIN FETCH p.author author
                LEFT JOIN FETCH author.team authorTeam
                LEFT JOIN FETCH authorTeam.members
                WHERE p.id = :pullRequestId
            """)
    Optional<PullRequest> findWithReviewersAndTheirTeams(@Param("pullRequestId") String pullRequestId);

    @Query("""
                SELECT p FROM PullRequest p
                LEFT JOIN FETCH p.assignedReviewers ar
                LEFT JOIN FETCH p.author author
                LEFT JOIN FETCH author.team authorTeam
                LEFT JOIN FETCH authorTeam.members
            """)
    List<PullRequest> findAllWithReviewersAndTheirTeams();
}
