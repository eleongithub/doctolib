package com.syscom.service.utils;


import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Junit pour le validateur de mail
 *
 * Created by Eric Legba on 15/08/17.
 */
public class MailValidatorTest {

    private static MailValidator mailValidator;

    @BeforeClass
    public static void initMailValidator() {
        mailValidator = new MailValidator();
    }

    @Test
    public void validEmailTest() {
        String mail="mymailValid@gmail.com";
        assertThat(mailValidator.validate(mail)).isTrue();

    }

    @Test
    public void inValidEmailTest() {
        String mail="mymail@sdscsz.comid@gmail.com";
        assertThat(mailValidator.validate(mail)).isFalse();
    }
}
