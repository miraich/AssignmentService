package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRs;
import com.andrey.assignmentservice.mapper.PullRequestMapper;
import com.andrey.assignmentservice.service.impl.PullRequestManagementServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pullRequest")
@RequiredArgsConstructor
public class PullRequestController {
    private final PullRequestMapper pullRequestMapper;
    private final PullRequestManagementServiceImpl pullRequestManagementService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public CreatePullRequestRs createPullRequest(@RequestBody @Valid CreatePullRequestRq createPullRequestRq) {
        return pullRequestMapper.mapToCreateRs(
                pullRequestManagementService.createPullRequest(pullRequestMapper.map(createPullRequestRq))
        );
    }

    @PostMapping("/merge")
    public MergePullRequestRs mergePullRequest(@RequestBody @Valid MergePullRequestRq mergePullRequestRq) {
        return pullRequestMapper.mapToMergeRs(
                pullRequestManagementService.mergePullRequest(mergePullRequestRq.pullRequestId())
        );
    }

    @PostMapping("/reassign")
    public ReassignPullRequestRs reassignPullRequest(@RequestBody @Valid ReassignPullRequestRq reassignPullRequestRq) {
        return pullRequestMapper.mapToReassignRs(
                pullRequestManagementService.reassignPullRequest(reassignPullRequestRq)
        );
    }
}
