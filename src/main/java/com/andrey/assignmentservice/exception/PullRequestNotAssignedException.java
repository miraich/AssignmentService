package com.andrey.assignmentservice.exception;

public class PullRequestNotAssignedException extends RuntimeException {
    public PullRequestNotAssignedException(String message) {
        super(message);
    }
}
