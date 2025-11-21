package com.andrey.assignmentservice.exception;

public class PullRequestNoCandidateException extends RuntimeException {
    public PullRequestNoCandidateException(String message) {
        super(message);
    }
}
