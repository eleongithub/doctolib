package com.syscom.dao;

import com.syscom.domains.models.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Test du repository des utilisateurs {@link User}
 *
 * Created by ansible on 21/06/17.
 */

public class UserRepositoryTest extends AbstractRepositoryTest{

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByLoginThenReturnUser() {
        // given
        User user = new User();
        user.setName(NAME);
        user.setLogin(LOGIN);
        user.setFirstName(FIRST_NAME);
        user.setPassword(PASSWORD);
        user  = entityManager.persistAndFlush(user);

        // when
        User foundUser = userRepository.findByLogin(LOGIN);

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(NAME);
        assertThat(foundUser.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(foundUser.getLogin()).isEqualTo(LOGIN);
        assertThat(foundUser.getPassword()).isEqualTo(PASSWORD);
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getCreateDate()).isNotNull();
        assertThat(foundUser.getUpdateDate()).isNotNull();

    }


    @Test
    public void whenExistsByLoginThenReturnBoolean() {
        // given
        User user = new User();
        user.setName(NAME);
        user.setLogin(LOGIN);
        user.setFirstName(FIRST_NAME);
        user.setPassword(PASSWORD);
        entityManager.persistAndFlush(user);

        // when
        boolean checkExistUserByLogin = userRepository.existsByLogin(LOGIN);

        // then
        assertThat(checkExistUserByLogin).isTrue();

        // when
        boolean checkExistUnknowUserByLogin = userRepository.existsByLogin("login_1");

        // then
        assertThat(checkExistUnknowUserByLogin).isFalse();
    }
}
