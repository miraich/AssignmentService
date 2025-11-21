package com.andrey.assignmentservice.mapper;

import com.andrey.assignmentservice.config.MapstructConfig;
import com.andrey.assignmentservice.dto.user.CreateUserRq;
import com.andrey.assignmentservice.dto.user.CreateUserRs;
import com.andrey.assignmentservice.dto.user.UpdateUserRq;
import com.andrey.assignmentservice.dto.user.UpdateUserRs;
import com.andrey.assignmentservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {
    @Mapping(source = "userId", target = "id")
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "pullRequests", ignore = true)
    User map(CreateUserRq createUserRq);

    @Mapping(target = "userId", source = "id")
    CreateUserRs map(User user);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "teamName", source = "team.teamName")
    UpdateUserRs mapToUpdateRs(User user);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "pullRequests", ignore = true)
    User mapFromUpdateRq(UpdateUserRq updateUserRs);

}
