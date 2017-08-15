package com.syscom.rest.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Eric Legba on 13/07/17.
 */
@Configuration
@ComponentScan(basePackages = {"com.syscom.service", "com.syscom.rest"})
@EntityScan(basePackages = "com.syscom.domains")
@EnableJpaRepositories(basePackages = {"com.syscom.dao"})
@EnableAutoConfiguration
public class ConfigIntegration {


}
