package com.syscom.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by ansible on 15/08/17.
 */
public class ApiTestConfiguration {
    @Bean(name = "javaMailSender")
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
