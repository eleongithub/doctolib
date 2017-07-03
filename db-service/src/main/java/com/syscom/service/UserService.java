package com.syscom.service;

import com.syscom.domains.dto.UserDTO;
import com.syscom.service.exceptions.BusinessException;

/**
 * Contrat d'interface des services métiers des utilisateurs
 *
 * @author el1638en
 * @since 09/06/17 17:19
 */
public interface UserService {

	/**
	 * Créer un nouvel utilisateur
	 *
	 * @param userDTO {@link UserDTO}
	 * @throws BusinessException {@link BusinessException}
     */
	void create(UserDTO userDTO) throws BusinessException;


}
