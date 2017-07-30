package com.syscom.service;

import com.syscom.domains.dto.UserDTO;
import com.syscom.service.exceptions.BusinessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Contrat d'interface des services métiers des utilisateurs {@link com.syscom.domains.models.User}
 *
 * @author el1638en
 * @since 09/06/17 17:19
 */
public interface UserService {

	/**
	 * Créer un nouvel utilisateur
	 * @param userDTO {@link UserDTO}
	 * @throws BusinessException {@link BusinessException}
     */
	void create(UserDTO userDTO) throws BusinessException;

	/**
	 * Authentification d'un utilisateur à partir d'une autorisation (Login:MDP(Base 64))
	 * @param authorization
	 * @return Jeton d'authentification
     */
	String authenticate(String authorization) throws UsernameNotFoundException, BusinessException;
}
