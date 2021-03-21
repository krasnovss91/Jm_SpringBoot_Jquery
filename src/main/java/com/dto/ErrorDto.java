package com.dto;

import javax.xml.soap.SAAJResult;

public class ErrorDto {
    private final String title;

    private final String message;

    public ErrorDto(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
