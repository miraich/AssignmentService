package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.pullrequest.GetUserPullRequestsRs;
import com.andrey.assignmentservice.dto.user.UpdateUserRq;
import com.andrey.assignmentservice.dto.user.UpdateUserRs;
import com.andrey.assignmentservice.mapper.PullRequestMapper;
import com.andrey.assignmentservice.mapper.UserMapper;
import com.andrey.assignmentservice.service.entity.UserService;
import com.andrey.assignmentservice.service.impl.PullRequestManagementServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PullRequestMapper pullRequestMapper;
    private final PullRequestManagementServiceImpl pullRequestManagementService;

    @PostMapping("/setIsActive")
    public UpdateUserRs setUserIsActive(@RequestBody @Valid UpdateUserRq updateUserRq) {
        return userMapper.mapToUpdateRs(userService.updateStatus(userMapper.mapFromUpdateRq(updateUserRq)));
    }

    @GetMapping("/getReview")
    public GetUserPullRequestsRs getUserPullRequests(@RequestParam("user_id") String userId) {
        return new GetUserPullRequestsRs(
                userId,
                pullRequestMapper.map(pullRequestManagementService.getUserPullRequests(userId))
        );
    }
}
