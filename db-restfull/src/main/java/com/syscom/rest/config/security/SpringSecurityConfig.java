package com.syscom.rest.config.security;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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

    private SpringSecurityConfig() {
        throw new IllegalAccessError("Spring Security Configuration class");
    }

    /**
     * Configuration de sécurité de l'API Rest secured dont l'acces est securisé par Token
     */
    @Configuration
    @Order(1)
    public static class SecuredApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Bean
        public TokenAuthenticationProvider tokenAuthenticationProvider() {
            return new TokenAuthenticationProvider();
        }

        @Bean
        public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
            TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter();
            tokenAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
            return tokenAuthenticationFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(tokenAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.anonymous().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable()
                    .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .antMatcher("/api/secured/**")
                    .authorizeRequests()
                    .anyRequest().authenticated();

        }
    }

    /**
     * Configuration de sécurité de l'API des utilisateurs pour s'enregistrer. API ouvert intégralement..
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

        @Bean
        @Override
        public UserDetailsService userDetailsService(){
            return new DbUserDetailsService();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
            authenticationProvider.setUserDetailsService(userDetailsService());
            return authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .addFilterAt(new BasicAuthenticationFilter(super.authenticationManager(), new BasicAuthenticationEntryPoint()), UsernamePasswordAuthenticationFilter.class)
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll();
        }

    }
}