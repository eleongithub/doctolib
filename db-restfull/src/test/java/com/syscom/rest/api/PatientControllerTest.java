package com.syscom.rest.api;

import com.syscom.domains.dto.PatientDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    private PatientController patientController;


    @Before
    public void setup() throws Exception {
        super.initMockMvc(patientController);
    }

    @Test
    public void whenCreatePatientWithoutAutorizationTokenThenReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                                            .content(json(createPatientDTO()))
                                            .contentType(contentType))
                                            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void whenCreatePatientWithInvalidAutorizationTokenThenReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .header(AUTHORIZATION, "token")
                .content(json(createPatientDTO()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void whenCreateUnvalidPatientThenBadClientRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                .header(AUTHORIZATION, getTokenForAdmin())
                .content(json(new PatientDTO()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void whenCreateValidPatientWithValidTokenThenReturnSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE)
                                            .header(AUTHORIZATION, getTokenForAdmin())
                                            .content(json(createPatientDTO()))
                                            .contentType(contentType))
                                            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private PatientDTO createPatientDTO(){
        return PatientDTO.builder()
                         .address(ADDRESS)
                         .mail(MAIL)
                         .firstName(FIRST_NAME)
                         .name(NAME)
                         .phone(PHONE)
                         .build();
    }
}
