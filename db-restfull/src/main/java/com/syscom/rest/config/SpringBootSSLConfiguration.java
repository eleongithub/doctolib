package com.syscom.rest.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Configuration SSL pour l'application spring boot.
 *
 * Created by Eric Legba on 20/08/17.
 */
@Configuration
public class SpringBootSSLConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringBootSSLConfiguration.class);

    @Value("${enabled.ssl}")
    private boolean enabledSSL;

    /**
     * Configuration SSL pour Spring Boot
     * @return {@link EmbeddedServletContainerFactory}
     */
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        if(!enabledSSL){
            LOGGER.info("SSL is disabled for Spring boot application");
            return new TomcatEmbeddedServletContainerFactory();
        }
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
//       Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//       Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//       connector.setScheme("https");
//       connector.setSecure(true);
//       connector.setPort(8443);
//       protocol.setSSLEnabled(true);
//       return connector;

        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("/keystore/keystore.jks").getFile();
            File truststore = new ClassPathResource("/keystore/keystore.jks").getFile();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(8443);
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("password");
            protocol.setTruststoreFile(truststore.getAbsolutePath());
            protocol.setTruststorePass("password");
            protocol.setKeyAlias("keyAlias");
            return connector;
        }catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }
}
