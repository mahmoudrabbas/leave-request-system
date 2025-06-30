package com.empSystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocaleMessage {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, String param) {
        String msg = messageSource.getMessage(code, new String[]{param}, LocaleContextHolder.getLocale());
        System.out.println(msg);
        return messageSource.getMessage(code, new String[]{param}, LocaleContextHolder.getLocale());
    }
}
