package com.syscom.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
 * Created by Eric Legba on 02/07/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter{

    /**
     * Configuration de l'API des utilisateurs. API non sécurisé.
     * TODO - Indiquer ici uniquement l'API signin, pour s'enregistrer
     * @return
     */
    @Bean
    public Docket apiUser() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Signin user API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syscom.rest"))
                .paths(PathSelectors.ant("/api/user/**"))
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Configuration de l'API Login. API accessible à tous.
     *
     * @return
     */
    @Bean
    public Docket apiLogin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Login API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syscom.rest"))
                .paths(PathSelectors.ant("/api/login/**"))
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Configuration des APIs sécursés
     *
     *
     * @return
     */
    @Bean
    public Docket apiSecured() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Secured API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syscom.rest"))
                .paths(PathSelectors.ant("/api/secured/**"))
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return  new ApiInfoBuilder().description("API - Doctolib.")
                                              .license("GNU License")
                                              .termsOfServiceUrl("API license URL")
                                              .title("Doctolib REST API")
                                              .contact(new Contact("Eric LEGBA", "homepage", "eric.legba@gmail.com"))
                                              .build();
    }

}
