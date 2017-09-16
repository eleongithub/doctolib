package com.syscom.service;

import com.syscom.dao.PatientRepository;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.models.Patient;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    private static final LocalDateTime BEGIN = LocalDateTime.now();
    private static final LocalDateTime END = BEGIN.plusMinutes(10);

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
        Patient patient = createPatient();
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(BEGIN)
                                                   .patientId(patient.getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    @Test(expected = BusinessException.class)
    public void createRendezVousWithBadDateThrowException() throws Exception {
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .dateBegin(END)
                                                   .dateEnd(BEGIN)
                                                   .patientId(createPatient().getId())
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    @Test
    public void createRendezVous() throws Exception {
        Patient patient = createPatient();
        rendezVousService.create(createRendezVousDTO(patient));
    }


    @Test
    public void findAll() throws Exception {
        Patient patient = createPatient();
        RendezVousDTO rendezVousDTO = createRendezVousDTO(patient);
        rendezVousService.create(rendezVousDTO);
        List<RendezVousDTO> lists = rendezVousService.findAll();
        assertThat(lists).isNotEmpty();
        assertThat(lists.size()).isEqualTo(1);
        RendezVousDTO result = lists.get(0);
        assertThat(result.getDateBegin()).isEqualTo(BEGIN);
        assertThat(result.getDateEnd()).isEqualTo(END);
        assertThat(result.getPatientId()).isEqualTo(patient.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findRendezVousWithNullId() throws Exception {
        RendezVousDTO rendezVousDTO = rendezVousService.findById(null);
    }

    @Test(expected = BusinessException.class)
    public void findRendezVousWithWrongId() throws Exception {
        RendezVousDTO rendezVousDTO = rendezVousService.findById(Long.valueOf(100));
        assertThat(rendezVousDTO).isNull();
    }


    private RendezVousDTO createRendezVousDTO(Patient patient){
        return RendezVousDTO.builder()
                            .dateBegin(BEGIN)
                            .dateEnd(END)
                            .patientId(patient.getId())
                            .build();
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
