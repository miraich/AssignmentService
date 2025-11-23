package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.error.ErrorRs;
import com.andrey.assignmentservice.dto.pullrequest.GetUserPullRequestsRs;
import com.andrey.assignmentservice.dto.user.UpdateUserRq;
import com.andrey.assignmentservice.dto.user.UpdateUserRs;
import com.andrey.assignmentservice.mapper.PullRequestMapper;
import com.andrey.assignmentservice.mapper.UserMapper;
import com.andrey.assignmentservice.service.entity.UserService;
import com.andrey.assignmentservice.service.impl.PullRequestManagementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "Операции с пользователями")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PullRequestMapper pullRequestMapper;
    private final PullRequestManagementServiceImpl pullRequestManagementService;

    @Operation(summary = "Установить флаг активности пользователя", description = "Установить флаг активности пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновлённый пользователь",
                    content = @Content(schema = @Schema(implementation = UpdateUserRs.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @PostMapping("/setIsActive")
    public UpdateUserRs setUserIsActive(@RequestBody @Valid UpdateUserRq updateUserRq) {
        return userMapper.mapToUpdateRs(userService.updateStatus(userMapper.mapFromUpdateRq(updateUserRq)));
    }

    @Operation(summary = "Получить PR'ы, где пользователь назначен ревьювером", description = "Получить список PR'ов, где пользователь назначен ревьювером")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список PR'ов пользователя",
                    content = @Content(schema = @Schema(implementation = GetUserPullRequestsRs.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @GetMapping("/getReview")
    public GetUserPullRequestsRs getUserPullRequests(@RequestParam("user_id") String userId) {
        return new GetUserPullRequestsRs(
                userId,
                pullRequestMapper.map(pullRequestManagementService.getUserPullRequests(userId))
        );
    }
}
