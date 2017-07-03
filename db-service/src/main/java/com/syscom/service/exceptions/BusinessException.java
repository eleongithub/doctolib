package com.syscom.service.exceptions;

/**
 * Classe d'exception pour gérer les erreurs metiers
 *
 * @author el1638en
 * @since 14/06/17 14:25
 */
public class BusinessException extends Exception {

	public BusinessException(final String message){
		super(message);
	}
}
