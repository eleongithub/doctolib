package com.syscom.service.impl;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import com.syscom.service.UserService;
import com.syscom.domains.dto.UserDTO;
import com.syscom.dao.UserRepository;
import com.syscom.domains.models.User;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implémentation des services métiers des utilisateurs {@link User}
 *
 * @author el1638en
 * @since 09/06/17 17:20
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messageSource;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public void create(UserDTO userDTO) throws BusinessException {

		Assert.notNull(userDTO, messageSource.getMessage("user.not.null", null, null));
//		1 - Verifier les données obligatoires
		List<String> errors = checkUserData(userDTO);
		if(!errors.isEmpty()){
			throw new BusinessException(StringUtils.join(errors," "));
		}

//		Vérifier que le login n'est pas déjà utilisé
		if(userRepository.existsByLogin(userDTO.getLogin())){
			throw new BusinessException(messageSource.getMessage("user.login.already.used", null, null));
		}

//		Construire l'Objet User à persister à partir du DTO
		User user = User.builder()
						.login(userDTO.getLogin())
						.firstName(userDTO.getFirstName())
						.name(userDTO.getName())
//						.password(passwordEncoder.encode(userDTO.getPassword()))
						.password(userDTO.getPassword())
						.build();

//		Inserer l'utilisateur en base de données
		 userRepository.save(user);

	}


	private List<String> checkUserData(UserDTO userDTO){

		List<String> errors = new ArrayList<String>();
		if(isEmpty(userDTO.getName())){
			errors.add(messageSource.getMessage("user.name.empty", null, null));
		}

		if(isEmpty(userDTO.getFirstName())){
			errors.add(messageSource.getMessage("user.firstname.empty", null, null));
		}

		if(isEmpty(userDTO.getLogin())){
			errors.add(messageSource.getMessage("user.login.empty", null, null));
		}

		if(isEmpty(userDTO.getPassword())){
			errors.add(messageSource.getMessage("user.password.empty", null, null));
		}

		return errors;
	}
}
