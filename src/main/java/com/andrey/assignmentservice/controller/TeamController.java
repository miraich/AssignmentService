package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.error.ErrorRs;
import com.andrey.assignmentservice.dto.team.CreateTeamRq;
import com.andrey.assignmentservice.dto.team.CreateTeamRs;
import com.andrey.assignmentservice.dto.team.GetTeamRs;
import com.andrey.assignmentservice.mapper.TeamMapper;
import com.andrey.assignmentservice.service.entity.TeamService;
import com.andrey.assignmentservice.service.impl.TeamManagementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Teams", description = "Операции с командами")
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamManagementServiceImpl teamManagementService;
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @Operation(summary = "Создать команду с участниками", description = "Создаёт команду с участниками (создаёт/обновляет пользователей)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Команда создана",
                    content = @Content(schema = @Schema(implementation = CreateTeamRs.class))),
            @ApiResponse(responseCode = "409", description = "Команда уже существует",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public CreateTeamRs createTeamWithMembers(@RequestBody @Valid CreateTeamRq createTeamRq) {
        return teamMapper.mapToCreateRs(teamManagementService.createTeamWithMembers(teamMapper.map(createTeamRq)));
    }

    @Operation(summary = "Получить команду с участниками", description = "Получить команду с участниками по имени команды")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объект команды",
                    content = @Content(schema = @Schema(implementation = GetTeamRs.class))),
            @ApiResponse(responseCode = "404", description = "Команда не найдена",
                    content = @Content(schema = @Schema(implementation = ErrorRs.class)))
    })
    @GetMapping("/get")
    public GetTeamRs getTeam(@RequestParam("team_name") String teamName) {
        return teamMapper.mapToGetRs(teamService.findByTeamNameWithMembers(teamName));
    }
}
