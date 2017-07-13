package com.syscom.rest.integration;

import com.syscom.domains.dto.UserDTO;
import com.syscom.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by ansible on 13/07/17.
 */
public class UserControllerIntegTest extends IntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() throws Exception{
        // prepare
        UserDTO userDTO = UserDTO.builder()
                .name("NAME")
                .firstName("FIRST_NAME")
                .password("PASSWORD")
                .login("LOGIN")
                .build();
        // execute
        ResponseEntity<UserDTO> responseEntity = testRestTemplate.postForEntity("/user", userDTO, null);

        // verify
        Assertions.assertThat(responseEntity.getStatusCode()).isNotEqualTo(HttpStatus.CREATED);

    }
}
