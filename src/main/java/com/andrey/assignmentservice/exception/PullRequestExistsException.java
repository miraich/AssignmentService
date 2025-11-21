package com.andrey.assignmentservice.exception;

import jakarta.persistence.EntityExistsException;

public class PullRequestExistsException extends EntityExistsException {
    public PullRequestExistsException(String message) {
        super(message);
    }
}
