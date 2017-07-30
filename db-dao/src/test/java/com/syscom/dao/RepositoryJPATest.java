package com.syscom.dao;

import com.syscom.dao.config.RepositoryTestConfiguration;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Eric Legba on 01/07/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
public abstract class RepositoryJPATest {

    // Données de test pour les rôles
    protected static final String ROLE_CODE = "CODE";
    protected static final String ROLE_LIBELLE = "LIBELLE";

    // Données de test pour les utilisateurs
    protected static final String USER_LOGIN = "LOGIN";
    protected static final String USER_NAME = "NAME";
    protected static final String USER_FIRST_NAME = "FIRST_NAME";
    protected static final String USER_PASSWORD = "PASSWORD";

    @Autowired
    protected TestEntityManager testEntityManager;

    protected Role getDefaultRole(){
        return Role.builder()
                .code(ROLE_CODE)
                .libelle(ROLE_LIBELLE)
                .build();
    }

    protected User getUser(Role role){
        return User.builder()
                    .name(USER_NAME)
                    .login(USER_LOGIN)
                    .firstName(USER_FIRST_NAME)
                    .password(USER_PASSWORD)
                    .role(role)
                    .build();
    }
}
