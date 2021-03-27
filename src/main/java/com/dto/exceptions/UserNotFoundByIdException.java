package com.dto.exceptions;

public class UserNotFoundByIdException extends Exception {
    private final long id;

    public UserNotFoundByIdException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
