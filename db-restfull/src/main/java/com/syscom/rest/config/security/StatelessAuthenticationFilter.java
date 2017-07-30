package com.syscom.rest.config.security;

import com.syscom.domains.models.Token;
import com.syscom.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter pour authentifier les requêtes des clients avec un jeton  (token) dans les en-têtes HTTP
 *
 * Created by Eric Legba on 26/07/17.
 */
//@Component
public class StatelessAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private TokenService tokenService;

    private static final String AUTH_HEADER_NAME = "AUTH-TOKEN";

    public StatelessAuthenticationFilter() {
//        Indiquer ici le pattern des URLs auxquels doivent s'appliquer le filter
        super("/api/secured/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }


    protected StatelessAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenValue = request.getHeader(AUTH_HEADER_NAME);
        if (tokenValue == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("No token found in request headers. Unauthorized access.");
        }

        Token token = tokenService.findValidTokenByValue(tokenValue);
        if (token == null) {
            throw new PreAuthenticatedCredentialsNotFoundException(
                    "Unvalid token. Unauthorized access.");
        }

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(tokenValue);

        return getAuthenticationManager().authenticate(authRequest);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
