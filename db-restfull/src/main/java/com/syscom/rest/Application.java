package com.syscom.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Eric Legba
 * @since 14/06/17 16:54
 */
@Configuration
@ComponentScan({"com.syscom.rest", "com.syscom.service"})
@EntityScan(basePackages = {"com.syscom.domains"})
@EnableJpaRepositories(basePackages = {"com.syscom.dao"})
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
