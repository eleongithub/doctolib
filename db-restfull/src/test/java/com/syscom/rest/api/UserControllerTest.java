package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.rest.AbstractAPITest;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by ansible on 03/07/17.
 */
public class UserControllerTest extends AbstractAPITest{

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";

    @Test(expected = BusinessException.class)
    public void whenCreateEmptyUserThenThrowBusinessException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(json(new UserDTO()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void createUser() throws Exception {
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
