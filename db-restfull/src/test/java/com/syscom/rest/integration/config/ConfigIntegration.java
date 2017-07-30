package com.syscom.rest.integration.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Eric Legba on 13/07/17.
 */
@Configuration
@EntityScan(basePackages = "com.syscom.domains")
@ComponentScan(basePackages = {"com.syscom.service", "com.syscom.rest"})
@EnableJpaRepositories(basePackages = {"com.syscom.dao"})
public class ConfigIntegration {
}
