package com.syscom.service.impl;

import com.syscom.dao.TokenRepository;
import com.syscom.dao.UserRepository;
import com.syscom.domains.dto.TokenDTO;
import com.syscom.domains.models.Token;
import com.syscom.domains.models.User;
import com.syscom.service.TokenService;
import com.syscom.service.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * Implémentation des services métiers des jetons d'authentification {@link com.syscom.domains.models.Token}
 *
 *
 * Created by Eric Legba on 27/07/17.
 */
@Service
@PropertySource("classpath:db-service.properties")
@Transactional(rollbackFor = Exception.class)
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Value("${token.duration}")
    private int tokenDuration;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public TokenDTO create(TokenDTO tokenDTO) throws BusinessException {
        LocalDateTime currentTime = LocalDateTime.now();
        tokenRepository.deleteExpiredToken(currentTime);
        User user = userRepository.findById(tokenDTO.getUserId());
        if(user==null){
            throw new BusinessException(messageSource.getMessage("user.token.mandatory", null, null));
        }
        LocalDateTime expirationDate = currentTime.plusMinutes(tokenDuration);
        String value = UUID.randomUUID().toString();
        Token token = Token.builder()
                           .dateExpiration(expirationDate)
                           .value(value)
                           .user(user)
                           .build();
        token = tokenRepository.save(token);
        return convertFrom(token, user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token findValidTokenByValue(String value) {
        Token token = tokenRepository.findByValue(value);
        if(token==null){
            return null;
        }
        if(token.getDateExpiration().isAfter(LocalDateTime.now())){
            return token;
        }else{
            return null;
        }
    }

    private TokenDTO convertFrom(Token token, User user){
        return TokenDTO.builder()
                        .value(token.getValue())
                        .userId(user.getId())
                        .dateExpiration(token.getDateExpiration())
                        .build();
    }
}
