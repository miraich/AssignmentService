package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.error.ErrorRs;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.CreatePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.MergePullRequestRs;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRq;
import com.andrey.assignmentservice.dto.pullrequest.ReassignPullRequestRs;
import com.andrey.assignmentservice.mapper.PullRequestMapper;
import com.andrey.assignmentservice.service.impl.PullRequestManagementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PullRequests", description = "Операции с Pull Request'ами")
@RestController
@RequestMapping("/pullRequest")
@RequiredArgsConstructor
public class PullRequestController {
    private final PullRequestMapper pullRequestMapper;
    private final PullRequestManagementServiceImpl pullRequestManagementService;

    @Operation(summary = "Создать PR и автоматически назначить до 2 ревьюверов",
            description = "Создать PR и автоматически назначить до 2 ревьюверов из команды автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PR создан",
                    content = @Content(schema = @Schema(implementation = CreatePullRequestRs.class))),
            @ApiResponse(responseCode = "404", description = "Автор/команда не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))),
            @ApiResponse(responseCode = "409", description = "PR уже существует",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public CreatePullRequestRs createPullRequest(@RequestBody @Valid CreatePullRequestRq createPullRequestRq) {
        return pullRequestMapper.mapToCreateRs(
                pullRequestManagementService.createPullRequest(pullRequestMapper.map(createPullRequestRq))
        );
    }

    @Operation(summary = "Пометить PR как MERGED", description = "Пометить PR как MERGED (идемпотентная операция)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PR в состоянии MERGED",
                    content = @Content(schema = @Schema(implementation = MergePullRequestRs.class))),
            @ApiResponse(responseCode = "404", description = "PR не найден",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @PostMapping("/merge")
    public MergePullRequestRs mergePullRequest(@RequestBody @Valid MergePullRequestRq mergePullRequestRq) {
        return pullRequestMapper.mapToMergeRs(
                pullRequestManagementService.mergePullRequest(mergePullRequestRq.pullRequestId())
        );
    }

    @Operation(summary = "Переназначить конкретного ревьювера",
            description = "Переназначить конкретного ревьювера на другого из его команды")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Переназначение выполнено",
                    content = @Content(schema = @Schema(implementation = ReassignPullRequestRs.class))),
            @ApiResponse(responseCode = "404", description = "PR или пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class))),
            @ApiResponse(responseCode = "409",
                    description = """
                            Возможные конфликты:
                            - Нельзя менять после MERGED
                            - Пользователь не был назначен ревьювером
                            - Нет доступных кандидатов
                            """,
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))

    })
    @PostMapping("/reassign")
    public ReassignPullRequestRs reassignPullRequest(@RequestBody @Valid ReassignPullRequestRq reassignPullRequestRq) {
        return pullRequestMapper.mapToReassignRs(
                pullRequestManagementService.reassignPullRequest(reassignPullRequestRq)
        );
    }
}
