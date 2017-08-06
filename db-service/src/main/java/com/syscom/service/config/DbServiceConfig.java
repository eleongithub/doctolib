package com.syscom.service.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration Spring pour la couche métier de l'application
 *
 * @author Eric Legba
 * @since 14/06/17 14:32
 */
@Configuration
public class DbServiceConfig {

	/**
	 * Configuration des ressources bundles
	 *
	 * @return
     */
	@Bean(name = "messageSource")
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * Bean Spring pour le chiffrement des mots de passe des utilisateurs
	 *
	 * @return
     */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}
}
