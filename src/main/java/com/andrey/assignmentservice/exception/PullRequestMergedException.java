package com.andrey.assignmentservice.exception;

public class PullRequestMergedException extends RuntimeException {
    public PullRequestMergedException(String message) {
        super(message);
    }
}
