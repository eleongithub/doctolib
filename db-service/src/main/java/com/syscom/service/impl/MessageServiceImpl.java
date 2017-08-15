package com.syscom.service.impl;

import com.syscom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Implémentation des services d'accès aux ressources bundle.
 *
 * Created by Eric Legba on 15/08/17.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(key, locale, new Object[]{});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Locale locale) {
        return getMessage(key,locale, new Object[]{});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Object... args) {
        return getMessage(key, LocaleContextHolder.getLocale(), args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Locale locale, Object... args) {
        return messageSource.getMessage(key,args,locale);
    }
}
