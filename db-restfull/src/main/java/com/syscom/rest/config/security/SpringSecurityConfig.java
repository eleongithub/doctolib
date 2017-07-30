package com.syscom.rest.config.security;

import com.syscom.dao.UserRepository;
import com.syscom.domains.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuration de Spring Security.
 *
 * Liste des ressources utilisés :
 *      - http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt
 *      - http://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
 *      - http://www.baeldung.com/securing-a-restful-web-service-with-spring-security
 *
 * Created by Eric Legba on 24/07/17.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {


    /**
     * Configuration de sécurité de l'API Rest secured dont l'acces est securisé par Token
     */
    @Configuration
    @Order(1)
    public static class SecuredApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserRepository userRepository;

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
            authenticationProvider.setUserDetailsService(userDetailsService());
            return authenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthenticationProvider());
        }

        @Bean
        @Override
        public UserDetailsService userDetailsService() {
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    User user = userRepository.findByLogin(username);
                    if (user != null) {
                        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList("USER"));
                    } else {
                        throw new UsernameNotFoundException("could not find the user '" + username + "'");
                    }
                }
            };
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.anonymous().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable()
                    .addFilterBefore(new StatelessAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .antMatcher("/api/secured/**")
                    .authorizeRequests()
                    .anyRequest().authenticated();

        }
    }

    /**
     * Configuration de sécurité de l'API des utilisateurs pour s'enregistrer. API ouvert intégralement.
     */
    @Configuration
    @Order(2)
    public static class UserApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .antMatcher("/api/user/**")
                    .authorizeRequests().anyRequest()
                    .anonymous();
        }

    }

    /**
     * Configuration de sécurité de l'API d'authentification. API ouvert intégralement.
     */
    @Configuration
    @Order(3)
    public static class LoginApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .antMatcher("/login/**")
                    .authorizeRequests().anyRequest()
                    .anonymous();
        }

    }
}