package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test sur le contrôleur de l'API des utilisateurs
 *
 * Created by Eric Legba on 03/07/17.
 */
public class UserControllerTest extends ControllerApiTest {


    private static final String RESOURCE = "/api/user";

    @Autowired
    private UserController userController;

    @Before
    public void setup() throws Exception {
        super.initMockMvc(userController);
    }

    @Test
    public void whenCreateNullUserThenThrowIllegalArgumentException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .content(json(null))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateEmptyUserThenThrowBusinessException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .content(json(new UserDTO()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateValidUserThenReturnSuccess() throws Exception {
        createAdminRole();
        UserDTO userDTO = UserDTO.builder()
                                 .name(USER_NAME)
                                 .firstName(USER_FIRST_NAME)
                                 .password(USER_PASSWORD)
                                 .login(USER_LOGIN)
                                 .role(Role.ADMIN.name())
                                 .build();
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .content(json(userDTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
