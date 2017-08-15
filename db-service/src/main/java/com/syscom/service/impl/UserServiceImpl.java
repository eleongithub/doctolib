package com.syscom.service.impl;

import com.syscom.dao.RoleRepository;
import com.syscom.dao.TokenRepository;
import com.syscom.dao.UserRepository;
import com.syscom.domains.dto.MailDTO;
import com.syscom.domains.dto.TokenDTO;
import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.models.Token;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import com.syscom.service.MailService;
import com.syscom.service.MessageService;
import com.syscom.service.TokenService;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private MessageService messageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(UserDTO userDTO) throws BusinessException {

		Assert.notNull(userDTO, messageService.getMessage("user.not.null"));

//		1 - Verifier les données obligatoires
		List<String> errors = checkUserData(userDTO);
		if(!errors.isEmpty()){
			throw new BusinessException(StringUtils.join(errors," "));
		}

//		Vérifier que le login n'est pas déjà utilisé
		if(userRepository.existsByLogin(userDTO.getLogin())){
			throw new BusinessException(messageService.getMessage("user.login.already.used"));
		}

//		Vérifier que le rôle renseigné existe
		Role role = roleRepository.findByCode(userDTO.getRole());
		if(role==null){
			throw new BusinessException(messageService.getMessage("user.role.unknown"));
		}

//		Construire l'Objet User à persister à partir du DTO
		User user = User.builder()
						.login(userDTO.getLogin())
						.firstName(userDTO.getFirstName())
						.name(userDTO.getName())
						.password(passwordEncoder.encode(userDTO.getPassword()))
						.mail(userDTO.getMail())
					    .role(role)
						.build();

//		Inserer l'utilisateur en BDD
		 userRepository.save(user);

//		Envoyer un mail de notification à l'utilisateur.
		Map<String,Object> datas = new HashMap<>();
		datas.put("name",user.getName());
		datas.put("firstname",user.getFirstName());
		datas.put("login",user.getLogin());
		String subject = messageService.getMessage("user.create.account.mail.subject");
		MailDTO mailDTO = MailDTO.builder()
								 .to(user.getMail())
								 .subject(subject)
								 .datas(datas)
								 .template("user-create-account")
								 .build();
		mailService.sendMessage(mailDTO);
	}

	/** {@inheritDoc} */
	@Override
	public String authenticate(String login) throws UsernameNotFoundException, BusinessException {

		Assert.notNull(login,messageService.getMessage("user.authentication.authorization.not.null"));

		User user = userRepository.findByLogin(login);
		if(user==null){
			throw new BadCredentialsException(messageService.getMessage("user.authentication.unkonwn.user"));
		}

		List<Token> tokens = tokenRepository.findByUser_Id(user.getId());
		if(CollectionUtils.isEmpty(tokens)){
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
	 *Créer un nouveau jeton de securité
	 *
	 * @param userId ID de l'utilisateur
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
	 * Méthode pour vérifier que les données obligatoires sont renseignées
	 *
	 * @param userDTO Données DTO de l'utilisateur {@link UserDTO}
	 * @return Liste de message d'erreurs
     */
	private List<String> checkUserData(UserDTO userDTO){
		List<String> errors = new ArrayList<>();
		if(isEmpty(userDTO.getName())){
			errors.add(messageService.getMessage("user.name.empty"));
		}

		if(isEmpty(userDTO.getFirstName())){
			errors.add(messageService.getMessage("user.firstname.empty"));
		}

		if(isEmpty(userDTO.getLogin())){
			errors.add(messageService.getMessage("user.login.empty"));
		}

		if(isEmpty(userDTO.getPassword())){
			errors.add(messageService.getMessage("user.password.empty"));
		}

		if(isEmpty(userDTO.getRole())){
			errors.add(messageService.getMessage("user.role.empty"));
		}
		return errors;
	}
}
