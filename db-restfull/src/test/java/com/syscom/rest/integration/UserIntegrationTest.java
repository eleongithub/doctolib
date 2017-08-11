package com.syscom.rest.integration;

import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.enums.Role;
import com.syscom.rest.api.UserController;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test d'intégration de l'API des utilisateurs.
 *
 * Created by Eric Legba on 13/07/17.
 */
public class UserIntegrationTest extends IntegrationTest {

    @Test
    public void createUser() throws Exception{
        // prepare
        UserDTO userDTO = UserDTO.builder()
                                 .name("NAME")
                                 .firstName("FIRST_NAME")
                                 .password("PASSWORD")
                                 .login("LOGIN")
                                 .role(Role.ADMIN.name())
                                 .build();

        // execute
        ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity(UserController.PATH, userDTO, null);

        // verify
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
}
