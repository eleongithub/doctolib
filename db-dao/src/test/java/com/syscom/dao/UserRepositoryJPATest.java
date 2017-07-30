package com.syscom.dao;

import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Test du repository JPA {@link UserRepository}
 *
 * Created by Eric Legba on 21/06/17.
 */

public class UserRepositoryJPATest extends RepositoryJPATest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByLoginThenReturnUser() {
        // given
        Role role = testEntityManager.persistAndFlush(getDefaultRole());
        User user  = testEntityManager.persistAndFlush(getUser(role));

        // when
        User foundUser = userRepository.findByLogin(USER_LOGIN);

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(USER_NAME);
        assertThat(foundUser.getFirstName()).isEqualTo(USER_FIRST_NAME);
        assertThat(foundUser.getLogin()).isEqualTo(USER_LOGIN);
        assertThat(foundUser.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getCreateDate()).isNotNull();
        assertThat(foundUser.getUpdateDate()).isNotNull();

    }


    @Test
    public void whenExistsByLoginThenReturnBoolean() {
        // given
        Role role = testEntityManager.persistAndFlush(getDefaultRole());
        testEntityManager.persistAndFlush(getUser(role));

        // when
        boolean checkExistUserByLogin = userRepository.existsByLogin(USER_LOGIN);

        // then
        assertThat(checkExistUserByLogin).isTrue();

        // when
        boolean checkExistUnknowUserByLogin = userRepository.existsByLogin("login_1");

        // then
        assertThat(checkExistUnknowUserByLogin).isFalse();
    }
}
