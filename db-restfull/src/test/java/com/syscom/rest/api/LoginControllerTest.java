package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.enums.Role;
import com.syscom.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * Test sur le controleur de l'API des authentifications.
 *
 * Created by Eric Legba on 01/08/17.
 */
public class LoginControllerTest extends ControllerApiTest{

    private static final String RESOURCE = "/api/login";

    @Autowired
    private LoginController loginController;

    @Autowired
    private UserService userService;

    @Before
    public void setup() throws Exception {
        super.initMockMvc(loginController);

    }

    @Test
    public void whenLoginUnkonwnUserThenThrowUnthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .header(HttpHeaders.AUTHORIZATION,builHeaderCredentials("user","secret")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void whenLoginUserWithWrongCredentilasThenThrowUnthorizedException() throws Exception {
        createAdminRole();
        UserDTO userDTO= UserDTO.builder()
                                .name(USER_NAME)
                                .firstName(USER_FIRST_NAME)
                                .password(USER_PASSWORD)
                                .login(USER_LOGIN)
                                .role(Role.ADMIN.name())
                                .build();
        userService.create(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .header(HttpHeaders.AUTHORIZATION,builHeaderCredentials("user","secret")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    public void whenLoginUserWitCredentilasThenReturnToken() throws Exception {
        createAdminRole();
        UserDTO userDTO= UserDTO.builder()
                                .name(USER_NAME)
                                .firstName(USER_FIRST_NAME)
                                .password(USER_PASSWORD)
                                .login(USER_LOGIN)
                                .role(Role.ADMIN.name())
                                .build();
        userService.create(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                                  .header(HttpHeaders.AUTHORIZATION,builHeaderCredentials(USER_LOGIN,USER_PASSWORD)))
                                  .andExpect(MockMvcResultMatchers.status().isOk());
    }



}
