package com.andrey.assignmentservice.service.impl;

import com.andrey.assignmentservice.dto.statistics.ReviewStatisticsRs;
import com.andrey.assignmentservice.enums.PullRequestStatus;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.model.User;
import com.andrey.assignmentservice.service.entity.PullRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsManagementServiceImpl {
    private final PullRequestService pullRequestService;

    public ReviewStatisticsRs getReviewStatistics() {
        List<PullRequest> allPullRequests = pullRequestService.findAllWithReviewersAndTheirTeams();

        Map<String, Long> assignmentsPerUser = calculateAssignmentsPerUser(allPullRequests);
        Map<String, Long> reviewsPerPullRequest = calculateReviewsPerPullRequest(allPullRequests);

        return new ReviewStatisticsRs(
                (long) allPullRequests.size(),
                countPullRequestsByStatus(allPullRequests, PullRequestStatus.OPEN),
                countPullRequestsByStatus(allPullRequests, PullRequestStatus.MERGED),
                assignmentsPerUser,
                reviewsPerPullRequest
        );
    }

    private Map<String, Long> calculateAssignmentsPerUser(List<PullRequest> pullRequests) {
        Map<String, Long> result = new HashMap<>();
        for (PullRequest pr : pullRequests) {
            for (User reviewer : pr.getAssignedReviewers()) {
                result.merge(reviewer.getId(), 1L, Long::sum);
            }
        }
        return result;
    }

    private Map<String, Long> calculateReviewsPerPullRequest(List<PullRequest> pullRequests) {
        Map<String, Long> result = new HashMap<>();
        for (PullRequest pr : pullRequests) {
            result.put(pr.getId(), (long) pr.getAssignedReviewers().size());
        }
        return result;
    }

    private Long countPullRequestsByStatus(List<PullRequest> pullRequests, PullRequestStatus status) {
        return pullRequests.stream()
                .filter(pr -> pr.getStatus() == status)
                .count();
    }
}
