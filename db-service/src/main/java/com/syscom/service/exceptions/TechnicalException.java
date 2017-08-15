package com.syscom.service.exceptions;

import lombok.Data;

/**
 * Created by Eric Legba on 15/08/17.
 */
@Data
public class TechnicalException extends RuntimeException {


    private String message;

    /**
     * Constructeur.
     *
     * @param message of exception.
     */
    public TechnicalException(final String message) {
        super(message);
        this.message = message;
    }
}
