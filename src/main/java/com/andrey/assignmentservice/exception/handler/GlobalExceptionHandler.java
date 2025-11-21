package com.andrey.assignmentservice.exception.handler;

import com.andrey.assignmentservice.dto.error.ErrorRs;
import com.andrey.assignmentservice.enums.ErrorCode;
import com.andrey.assignmentservice.exception.PullRequestExistsException;
import com.andrey.assignmentservice.exception.PullRequestMergedException;
import com.andrey.assignmentservice.exception.PullRequestNoCandidateException;
import com.andrey.assignmentservice.exception.PullRequestNotAssignedException;
import com.andrey.assignmentservice.exception.TeamExistsException;
import com.andrey.assignmentservice.exception.UserExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TeamExistsException.class)
    public ErrorRs handleEntityNotFoundException(TeamExistsException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.TEAM_EXISTS)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserExistsException.class)
    public ErrorRs handleEntityNotFoundException(UserExistsException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.USER_EXISTS)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PullRequestExistsException.class)
    public ErrorRs handleEntityNotFoundException(PullRequestExistsException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.PR_EXISTS)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PullRequestMergedException.class)
    public ErrorRs handleEntityNotFoundException(PullRequestMergedException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.PR_MERGED)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PullRequestNoCandidateException.class)
    public ErrorRs handleEntityNotFoundException(PullRequestNoCandidateException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.NO_CANDIDATE)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PullRequestNotAssignedException.class)
    public ErrorRs handleEntityNotFoundException(PullRequestNotAssignedException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.NOT_ASSIGNED)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorRs handleEntityNotFoundException(EntityNotFoundException e) {
        return ErrorRs.builder()
                .message(e.getMessage())
                .code(ErrorCode.NOT_FOUND)
                .build();
    }
}
