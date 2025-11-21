package com.andrey.assignmentservice.exception;

import jakarta.persistence.EntityExistsException;

public class TeamExistsException extends EntityExistsException {
    public TeamExistsException(String message) {
        super(message);
    }
}
