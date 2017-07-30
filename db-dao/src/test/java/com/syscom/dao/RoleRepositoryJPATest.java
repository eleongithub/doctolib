package com.syscom.dao;

import com.syscom.domains.models.referentiels.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *  Test du repository JPA {@link RoleRepository}
 *
 * Created by Eric Legba on 29/07/17.
 */
public class RoleRepositoryJPATest extends RepositoryJPATest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenFindByCodeThenReturnRole() {
        // given
        testEntityManager.persistAndFlush(getDefaultRole());

        // when
        Role resultRole = roleRepository.findByCode(ROLE_CODE);

        // then
        assertThat(resultRole).isNotNull();
        assertThat(resultRole.getCode()).isEqualTo(ROLE_CODE);
        assertThat(resultRole.getLibelle()).isEqualTo(ROLE_LIBELLE);
        assertThat(resultRole.getCreateDate()).isNotNull();
        assertThat(resultRole.getUpdateDate()).isNotNull();

    }
}
