package com.syscom.service;

import java.util.Locale;

/**
 * Contrat d'interface des services pour accéder aux messages resources
 *
 * Created by Eric Legba on 15/08/17.
 */
public interface MessageService {

    /**
     * Retourner le message bundle identifié par la clé en paramètre
     *
     * @param key clé du message bundle
     * @return le message bundle.
     */
    String getMessage(String key);

    /**
     * Retourner le message bundle identifié par la clé en paramètre.
     *
     * @param key clé du message bundle
     * @param locale
     * @return le message bundle.
     */
    String getMessage(String key, Locale locale);


    /**
     * Retourner le message bundle identifié par la clé en paramètre.
     *
     * @param key clé du message bundle
     * @param args arguments
     * @return le message bundle.
     */
    String getMessage(String key, Object... args);

    /**
     * Retourner le message bundle identifié par la clé en paramètre.
     *
     * @param key clé du message bundle
     * @param locale
     * @param args
     * @return e message bundle.
     */
    String getMessage(String key, Locale locale,  Object... args);

}
