package com.rest;

import com.dto.ErrorDto;
import com.dto.exceptions.UserNotFoundByIdException;
import com.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import static com.config.messages.codes.UserMessageCode.USER_NOT_FOUND_BY_ID_MESSAGE;
import static com.config.messages.codes.UserMessageCode.USER_NOT_FOUND_BY_ID_TITLE;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageService messageService;

    @ExceptionHandler(value = {UserNotFoundByIdException.class})
    protected ResponseEntity<ErrorDto> handleException(UserNotFoundByIdException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String userId = String.valueOf(ex.getId());

        String title = messageService.createMessage(locale, USER_NOT_FOUND_BY_ID_TITLE.getCode(), userId);
        String message = messageService.createMessage(locale, USER_NOT_FOUND_BY_ID_MESSAGE.getCode(), userId);

        ErrorDto errorDto = new ErrorDto(title, message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(value = {NoSuchMessageException.class})
    protected ResponseEntity<ErrorDto> handleMessageException(NoSuchMessageException ex, WebRequest request) {
        String title = "Server error";
        String message = "Server error happened, try again later";

        ErrorDto errorDto = new ErrorDto(title, message);

        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}