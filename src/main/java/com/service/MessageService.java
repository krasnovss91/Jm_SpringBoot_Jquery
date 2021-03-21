package com.service;

import java.util.Locale;

public interface MessageService {
    String createMessage(Locale locale, String messageKey, Object... args);


}
