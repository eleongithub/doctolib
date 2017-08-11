package com.syscom.rest.api;

import com.syscom.domains.dto.PatientDTO;
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
 *
 * Created by Eric Legba on 01/08/17.
 */
public class PatientControllerTest extends ControllerApiTest{

    private static final String RESOURCE = "/api/secured/patient";
    private static final String ADDRESS = "ADDRESS";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PHONE = "PHONE";
    private static final String MAIL = "MAIL";

    @Autowired
    private PatientController patientcontroller;

    @Autowired
    private UserService userService;

    private String token;

    @Before
    public void setup() throws Exception {
        super.initMockMvc(patientcontroller);
        createAdminRole();
        UserDTO userDTO= UserDTO.builder()
                .name(USER_NAME)
                .firstName(USER_FIRST_NAME)
                .password(USER_PASSWORD)
                .login(USER_LOGIN)
                .role(Role.ADMIN.name())
                .build();
        userService.create(userDTO);
        this.token = userService.authenticate(USER_LOGIN);
    }

    @Test
    public void whenCreateValidPatientThenReturnSuccess() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                                          .address(ADDRESS)
                                          .mail(MAIL)
                                          .firstName(FIRST_NAME)
                                          .name(NAME)
                                          .phone(PHONE)
                                          .build();
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                                            .header(HttpHeaders.AUTHORIZATION,this.token)
                                            .content(json(patientDTO))
                                            .contentType(contentType))
                                            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
