package com.syscom.rest.api;

import com.syscom.dao.RoleRepository;
import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.syscom.domains.enums.Role.ADMIN;

/**
 * Created by Eric Legba on 03/07/17.
 */
public class UserControllerTest extends ControllerApiTest {

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";

    private static final String RESOURCE = "/api/user";

    @Autowired
    private UserController userController;

    @Autowired
    private RoleRepository roleRepository;

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
        com.syscom.domains.models.referentiels.Role role = com.syscom.domains.models.referentiels.Role.builder()
                .libelle("LIBELLE")
                .code(ADMIN.name())
                .build();
        roleRepository.save(role);
        UserDTO userDTO = UserDTO.builder()
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .login(LOGIN)
                                 .role(Role.ADMIN.name())
                                 .build();
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .content(json(userDTO))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
