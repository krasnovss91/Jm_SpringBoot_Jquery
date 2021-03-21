package com.config.messages.codes;

public enum UserMessageCode {
    USER_NOT_FOUND_BY_ID_TITLE("user_error_title_not_found_by_id"),
    USER_NOT_FOUND_BY_ID_MESSAGE("user_error_message_not_found_by_id");

    private final String code;

    UserMessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}