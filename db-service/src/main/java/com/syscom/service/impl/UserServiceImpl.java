package com.syscom.service.impl;

import com.syscom.dao.RoleRepository;
import com.syscom.dao.TokenRepository;
import com.syscom.dao.UserRepository;
import com.syscom.domains.dto.TokenDTO;
import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.models.Token;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import com.syscom.service.TokenService;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Implémentation des services métiers des utilisateurs {@link User}
 *
 * @author Eric Legba
 * @since 09/06/17 17:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
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

//		Vérifier que le rôle renseigné existe
		Role role = roleRepository.findByCode(userDTO.getRole());
		if(role==null){
			throw new BusinessException(messageSource.getMessage("user.role.unknown", null, null));
		}

//		Construire l'Objet User à persister à partir du DTO
		User user = User.builder()
						.login(userDTO.getLogin())
						.firstName(userDTO.getFirstName())
						.name(userDTO.getName())
						.password(passwordEncoder.encode(userDTO.getPassword()))
					    .role(role)
						.build();

//		Inserer l'utilisateur en BDD
		 userRepository.save(user);

//		TODO - Envoyer un mail à l'utilisateur pour lui notifier la création de son compte utilisateur
	}

	/** {@inheritDoc} */
	@Override
	public String authenticate(String login) throws UsernameNotFoundException, BusinessException {

		Assert.notNull(login,messageSource.getMessage("user.authentication.authorization.not.null", null, null));

		User user = userRepository.findByLogin(login);
		if(user==null){
			throw new BadCredentialsException(messageSource.getMessage("user.authentication.unkonwn.user", null, null));
		}

		List<Token> tokens = tokenRepository.findByUser_Id(user.getId());
		if(tokens.isEmpty()){
//			Créer un nouveau jeton
			TokenDTO tokenDTO = createToken(user.getId());
			return tokenDTO.getValue();
		}

		Token token = tokens.get(0);
		if(token.getDateExpiration().isBefore(LocalDateTime.now())){
//			Supression du jeton expiré
			tokenRepository.delete(token);
//			Créer un nouveau jeton
			TokenDTO tokenDTO = createToken(user.getId());
			return tokenDTO.getValue();
		}else{
			return token.getValue();
		}
	}


	/**
	 *
	 * @param userId
	 * @return
	 * @throws BusinessException
     */
	private TokenDTO createToken(Long userId) throws BusinessException {
		TokenDTO tokenDTO = TokenDTO.builder()
									.userId(userId)
									.build();
		return tokenService.create(tokenDTO);
	}
	/**
	 *
	 * Méthode pour vérifier que les données obligatoires sont rensiegnées
	 *
	 * @param userDTO Données DTO de l'utilisateur {@link UserDTO}
	 * @return Liste de message d'erreurs
     */
	private List<String> checkUserData(UserDTO userDTO){
		List<String> errors = new ArrayList<>();
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

		if(isEmpty(userDTO.getRole())){
			errors.add(messageSource.getMessage("user.role.empty", null, null));
		}
		return errors;
	}
}
