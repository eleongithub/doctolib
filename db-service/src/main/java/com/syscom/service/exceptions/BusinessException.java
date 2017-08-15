package com.syscom.service.exceptions;

import lombok.Data;

/**
 * Classe d'exception pour gérer les erreurs metiers
 *
 * @author Eric Legba
 * @since 14/06/17 14:25
 */
@Data
public class BusinessException extends Exception {

	private String message;

	public BusinessException(String message){
		super(message);
		this.message = message;
	}
}
