package com.syscom.rest.config.security;

import com.syscom.domains.models.Token;
import com.syscom.domains.models.referentiels.Fonctionnalite;
import com.syscom.domains.utils.Fonctionnalites;
import com.syscom.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provider d'authentification par jeton
 *
 * Created by Eric Legba on 02/08/17.
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = authentication.getName();
        Token token = tokenService.findValidTokenByValue(accessToken);
        if(token == null){
            throw new BadCredentialsException("Unvalid token. Unauthorized access.");
        }
        List<Fonctionnalite> fonctionnalites = token.getUser().getRole().getFonctionnalites();
        List<GrantedAuthority> grantedAuthorities = fonctionnalites.stream().map(fonctionnalite -> new SimpleGrantedAuthority(Fonctionnalites.ROLE_PREFIX+fonctionnalite.getCode()))
                                                                            .collect(Collectors.toList());
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(accessToken, grantedAuthorities);
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
