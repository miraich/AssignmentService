package com.andrey.assignmentservice.controller;

import com.andrey.assignmentservice.dto.team.CreateTeamRq;
import com.andrey.assignmentservice.dto.team.CreateTeamRs;
import com.andrey.assignmentservice.dto.team.GetTeamRs;
import com.andrey.assignmentservice.mapper.TeamMapper;
import com.andrey.assignmentservice.service.entity.TeamService;
import com.andrey.assignmentservice.service.impl.TeamManagementServiceImpl;
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

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamManagementServiceImpl teamManagementService;
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public CreateTeamRs createTeamWithMembers(@RequestBody @Valid CreateTeamRq createTeamRq) {
        return teamMapper.mapToCreateRs(teamManagementService.createTeamWithMembers(teamMapper.map(createTeamRq)));
    }

    @GetMapping("/get")
    public GetTeamRs getTeam(@RequestParam("team_name") String teamName) {
        return teamMapper.mapToGetRs(teamService.findByTeamNameWithMembers(teamName));
    }
}
