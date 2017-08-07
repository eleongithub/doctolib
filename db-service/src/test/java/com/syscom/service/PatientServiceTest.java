package com.syscom.service;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.service.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test(expected = IllegalArgumentException.class)
    public void findPatientByNullIdThrowException() throws Exception {
        patientService.findById(null);
    }

    @Test
    public void findAllPatients() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .name(NAME)
                .firstName(FIRST_NAME)
                .phone(PHONE)
                .address(ADDRESS)
                .mail(MAIL)
                .build();
        patientService.create(patientDTO);

        List<PatientDTO> patients = patientService.findAll();
        assertThat(patients).isNotNull();
        assertThat(patients.size()).isEqualTo(1);

    }

    @Test
    public void findPatientById() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                                          .name(NAME)
                                          .firstName(FIRST_NAME)
                                          .phone(PHONE)
                                          .address(ADDRESS)
                                          .mail(MAIL)
                                          .build();
        patientService.create(patientDTO);

        List<PatientDTO> patients = patientService.findAll();

        PatientDTO result = patientService.findById(patients.get(0).getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(patientDTO.getName());
        Assertions.assertThat(result.getFirstName()).isEqualTo(patientDTO.getFirstName());
        Assertions.assertThat(result.getPhone()).isEqualTo(patientDTO.getPhone());
        Assertions.assertThat(result.getAddress()).isEqualTo(patientDTO.getAddress());
        Assertions.assertThat(result.getMail()).isEqualTo(patientDTO.getMail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePatientWithoutIdThrowException() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .name(NAME)
                .firstName(FIRST_NAME)
                .phone(PHONE)
                .address(ADDRESS)
                .mail(MAIL)
                .build();
        patientService.create(patientDTO);
        patientService.update(null,patientDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePatientWithoutPatientDTOThrowException() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                .name(NAME)
                .firstName(FIRST_NAME)
                .phone(PHONE)
                .address(ADDRESS)
                .mail(MAIL)
                .build();
        patientService.create(patientDTO);
        List<PatientDTO> patients = patientService.findAll();
        PatientDTO result = patientService.findById(patients.get(0).getId());
        patientService.update(result.getId(),null);
    }

    @Test
    public void updatePatient() throws Exception {
        PatientDTO patientDTO = PatientDTO.builder()
                                        .name(NAME)
                                        .firstName(FIRST_NAME)
                                        .phone(PHONE)
                                        .address(ADDRESS)
                                        .mail(MAIL)
                                        .build();
        patientService.create(patientDTO);
        List<PatientDTO> patients = patientService.findAll();
        PatientDTO result = patientService.findById(patients.get(0).getId());

        result.setPhone("NEW_PHONE");
        result.setMail("NEW_MAIL");
        result.setAddress("NEW ADDRESS");
        patientService.update(result.getId(),result);
    }
}
