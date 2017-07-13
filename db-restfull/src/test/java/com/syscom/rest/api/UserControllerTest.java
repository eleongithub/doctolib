package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by ansible on 03/07/17.
 */
public class UserControllerTest extends ControllerApiTest {

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";

    @Autowired
    private UserController userController;

    @Before
    public void setup() throws Exception {
        super.initMockMvc(userController);
    }

    @Test
    public void whenCreateNullUserThenThrowIllegalArgumentException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(json(null))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateEmptyUserThenThrowBusinessException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(json(new UserDTO()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateValidUserThenReturnSuccess() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .login(LOGIN)
                                 .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(json(userDTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
