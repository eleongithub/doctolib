package com.syscom.service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Eric Legba on 01/07/17.
 */
@Configuration
@EntityScan(basePackages = "com.syscom.domains")
@ComponentScan(basePackages = {"com.syscom.service"})
@EnableJpaRepositories(basePackages = {"com.syscom.dao"})
public class ServiceTestConfiguration {

    @Bean(name = "javaMailSender")
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}
}
