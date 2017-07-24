package com.syscom.service.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration liée à la couche métier de l'application
 * @author el1638en
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
	 * Bean Spring pour le chiffrement des mots de passe
	 *
	 * @return
     */
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return  new BCryptPasswordEncoder();
//	}
}
