package com.syscom.dao;

import com.syscom.domains.models.Token;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test du repository JPA {@link TokenRepository}
 *
 * Created by Eric Legba on 29/07/17.
 */
public class TokenRepositoryJPATest extends RepositoryJPATest {

    private static final String VALUE = "VALUE";

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void whenFindByValueThenReturnToken() {

        // given
        Role role = testEntityManager.persistAndFlush(getDefaultRole());
        User user  = testEntityManager.persistAndFlush(getUser(role));

        Token token = Token.builder()
                           .value(VALUE)
                           .dateExpiration(LocalDateTime.now())
                           .user(user)
                           .build();

        testEntityManager.persistAndFlush(token);

        // when
        Token resultToken = tokenRepository.findByValue(VALUE);

        // then
        assertThat(resultToken).isNotNull();
        assertThat(resultToken.getUser()).isEqualTo(user);
        assertThat(resultToken.getValue()).isEqualTo(VALUE);
        assertThat(resultToken.getCreateDate()).isNotNull();
        assertThat(resultToken.getUpdateDate()).isNotNull();

    }

    @Test
    public void whenFindByUserIdThenReturnToken() {

        // given
        Role role = testEntityManager.persistAndFlush(getDefaultRole());
        User user  = testEntityManager.persistAndFlush(getUser(role));

        Token token = Token.builder()
                .value(VALUE)
                .dateExpiration(LocalDateTime.now())
                .user(user)
                .build();

        testEntityManager.persistAndFlush(token);

        // when
        Token resultToken = tokenRepository.findByuserId(user.getId());

        // then
        assertThat(resultToken).isNotNull();
        assertThat(resultToken.getUser()).isEqualTo(user);
        assertThat(resultToken.getValue()).isEqualTo(VALUE);
        assertThat(resultToken.getCreateDate()).isNotNull();
        assertThat(resultToken.getUpdateDate()).isNotNull();

    }


    @Test
    public void whenDeleteExpiredTokenThenReturnNothing() {

        // given
        Role role = testEntityManager.persistAndFlush(getDefaultRole());
        User user  = testEntityManager.persistAndFlush(getUser(role));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.minusMinutes(10);
        Token token = Token.builder()
                .value(VALUE)
                .dateExpiration(expirationDate)
                .user(user)
                .build();

        testEntityManager.persistAndFlush(token);

        // when
        tokenRepository.deleteExpiredToken(now);

        // then
        assertThat(tokenRepository.findAll()).isEmpty();

    }
}
