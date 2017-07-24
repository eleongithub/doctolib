package com.syscom.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * Configuration de la documentation de l'API avec Swagger ( Voir http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
 *
 * Created by ansible on 02/07/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return  new ApiInfoBuilder().description("Some custom description of API.")
                                              .license("License of API")
                                              .termsOfServiceUrl("API license URL")
                                              .title("My REST API")
                                              .contact(new Contact("Eric LEGBA", "localhost", "eric.legba@gmail.com"))
                                              .build();
    }

}
