package com.syscom.service;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test des services métiers des patients
 *
 * Created by Eric Legba on 01/08/17.
 */
public class PatientServiceTest extends ServiceTest {

    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PHONE = "PHONE";
    private static final String ADDRESS = "ADDRESS";
    private static final String MAIL = "MAIL";

    @Autowired
    private PatientService patientService;

    @Test(expected = IllegalArgumentException.class)
    public void createNullPatientThrowException() throws Exception {
        patientService.create(null);
    }

    @Test(expected = BusinessException.class)
    public void createPatientWithEmptyDataThrowException() throws Exception {
        patientService.create(new PatientDTO());
    }

    @Test(expected = BusinessException.class)
    public void createPatientWithoutMandatoriesDatasThrowException() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                                          .address(ADDRESS)
                                          .mail(MAIL)
                                          .build();
        patientService.create(patientDTO);
    }

    public void createPatient() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                                          .name(NAME)
                                          .firstName(FIRST_NAME)
                                          .phone(PHONE)
                                          .address(ADDRESS)
                                          .mail(MAIL)
                                          .build();
        patientService.create(patientDTO);
    }
}
