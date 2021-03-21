package com.dto.exceptions;

public class UserNotFoundByIdException extends BaseException {
    private final long id;

    public UserNotFoundByIdException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
