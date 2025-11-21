package com.andrey.assignmentservice.service.entity;

import com.andrey.assignmentservice.model.User;

public interface UserService {
    User save(User user);

    User get(String userId);

    User update(User user);

    boolean exists(String userId);
}
