package com.andrey.assignmentservice.mapper;

import com.andrey.assignmentservice.config.MapstructConfig;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.GetUserPullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestDto;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRs;
import com.andrey.assignmentservice.model.PullRequest;
import com.andrey.assignmentservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapstructConfig.class)
public interface PullRequestMapper {

    @Mapping(target = "id", source = "pullRequestId")
    @Mapping(target = "name", source = "pullRequestName")
    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignedReviewers", ignore = true)
    @Mapping(target = "mergedAt", ignore = true)
    PullRequest map(CreatePullRequestRq createPullRequestRq);

    @Mapping(target = "pullRequestId", source = "id")
    @Mapping(target = "pullRequestName", source = "name")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assignedReviewers", expression = "java(mapUsersToIds(pullRequest.getAssignedReviewers()))")
    CreatePullRequestRs mapToCreateRs(PullRequest pullRequest);

    @Mapping(target = "pullRequestId", source = "id")
    @Mapping(target = "pullRequestName", source = "name")
    @Mapping(target = "authorId", source = "author.id")
    GetUserPullRequestRs mapToGetRs(PullRequest pullRequest);

    @Mapping(target = "pullRequestId", source = "id")
    @Mapping(target = "pullRequestName", source = "name")
    @Mapping(target = "authorId", source = "author.id")
    MergePullRequestRs mapToMergeRs(PullRequest pullRequest);

    @Mapping(target = "pullRequestId", source = "pullRequest.id")
    @Mapping(target = "pullRequestName", source = "pullRequest.name")
    @Mapping(target = "authorId", source = "pullRequest.author.id")
    @Mapping(target = "status", source = "pullRequest.status")
    @Mapping(target = "assignedReviewers", source = "pullRequest.assignedReviewers")
    ReassignPullRequestRs mapToReassignRs(ReassignPullRequestDto pullRequest);

    Set<GetUserPullRequestRs> map(Set<PullRequest> pullRequests);

    default Set<String> mapUsersToIds(Set<User> users) {
        if (users == null) {
            return new HashSet<>();
        }
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
