package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;


    @Override
    public String createMessage(Locale locale, String messageKey, Object... args) {

        return messageSource.getMessage(messageKey, args, locale);

    }
}
