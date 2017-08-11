package com.syscom.service;

import com.syscom.dao.PatientRepository;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.models.Patient;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

/**
 * Test des services métiers des rendez-vous.
 *
 * Created by Eric Legba on 11/08/17.
 */
public class RendezVousServiceTest extends ServiceTest {

    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PHONE = "PHONE";
    private static final String ADDRESS = "ADDRESS";
    private static final String MAIL = "MAIL";

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private PatientRepository patientRepository;

    @Test(expected = IllegalArgumentException.class)
    public void createNullRendezVousThrowException() throws Exception {
        rendezVousService.create(null);
    }

    @Test(expected = BusinessException.class)
    public void createRendezVousWithEmptyDataThrowException() throws Exception {
        rendezVousService.create(new RendezVousDTO());
    }

    @Test(expected = BusinessException.class)
    public void createRendezVousWithoutAllMandatoriesDatasThrowException() throws Exception {
        LocalDateTime begin = LocalDateTime.now();
        Patient patient = createPatient();
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(begin)
                                                   .patientId(patient.getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    @Test(expected = BusinessException.class)
    public void createRendezVousWithBadDateThrowException() throws Exception {
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.minusMinutes(10);
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(begin)
                                                   .dateEnd(end)
                                                   .patientId(createPatient().getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    @Test
    public void createRendezVous() throws Exception {
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.plusMinutes(10);
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(begin)
                                                   .dateEnd(end)
                                                   .patientId(createPatient().getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }


    @Test
    public void findAll() throws Exception {
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.plusMinutes(10);
        Patient patient = createPatient();
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(begin)
                                                   .dateEnd(end)
                                                   .patientId(patient.getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findRendezVousWithWrongId() throws Exception {
        RendezVousDTO rendezVousDTO = rendezVousService.findById(null);
    }

    private Patient createPatient(){
        Patient patient = Patient.builder()
                                 .address(ADDRESS)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .mail(MAIL)
                                 .phone(PHONE)
                                 .build();
       return patientRepository.save(patient);
    }
}
