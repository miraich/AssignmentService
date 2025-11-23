package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.User;

public interface UserService {
    User create(User user);

    User save(User user);

    User get(String userId);

    User getWithPullRequest(String userId);

    User getWithTeam(String userId);

    User getWithTeamAndMembers(String userId);

    User updateStatus(User user);

    boolean exists(String userId);
}
