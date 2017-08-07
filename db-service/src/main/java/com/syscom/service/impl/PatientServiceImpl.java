package com.syscom.service.impl;

import com.syscom.dao.PatientRepository;
import com.syscom.domains.dto.PatientDTO;
import com.syscom.domains.models.Patient;
import com.syscom.service.PatientService;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Implémentation des services métiers des patients {@link com.syscom.domains.models.Patient}
 *
 * Created by Eric Legba on 01/08/17.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PatientServiceImpl implements PatientService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(PatientDTO patientDTO) throws BusinessException {
        Assert.notNull(patientDTO, messageSource.getMessage("patient.not.null", null, null));
        List<String> errors = checkPatientData(patientDTO);

//		1 - Verifier les données obligatoires
        if(!errors.isEmpty()){
            throw new BusinessException(StringUtils.join(errors," "));
        }

//      2 - Construire l'objet Java à enregistrer en base de données
        Patient patient = Patient.builder()
                                 .address(patientDTO.getAddress())
                                 .name(patientDTO.getName())
                                 .firstName(patientDTO.getFirstName())
                                 .mail(patientDTO.getMail())
                                 .phone(patientDTO.getPhone())
                                 .build();
        patientRepository.save(patient);
//		TODO - Envoyer un mail au patient pour lui notifier la création de son compte
    }

    @Override
    public PatientDTO findById(Long id) throws BusinessException {
        Assert.notNull(id, messageSource.getMessage("patient.id.not.null", null, null));
        Patient patient = patientRepository.findOne(id);
        if(patient==null){
            throw new BusinessException(messageSource.getMessage("patient.unknown", null, null));
        }
        return convertToDTO(patient);

    }

    @Override
    public List<PatientDTO> findAll() {
        Iterable<Patient> patients = patientRepository.findAll();
        return StreamSupport.stream(patients.spliterator(), false)
                     .map(patient -> convertToDTO(patient))
                     .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientDTO update(Long id, PatientDTO patientDTO) throws BusinessException {
        Assert.notNull(id, messageSource.getMessage("patient.id.not.null", null, null));
        Assert.notNull(patientDTO, messageSource.getMessage("patient.not.null", null, null));
        Patient patient = patientRepository.findOne(id);

        if(patient==null || patient.getId() != patientDTO.getId()){
            throw new BusinessException(messageSource.getMessage("patient.unknown", null, null));
        }
        patient.setAddress(patientDTO.getAddress());
        patient.setMail(patientDTO.getMail());
        patient.setPhone(patientDTO.getPhone());
        patientRepository.save(patient);
        return convertToDTO(patient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws BusinessException {
        Assert.notNull(id, messageSource.getMessage("patient.id.not.null", null, null));
        Patient patient = patientRepository.findOne(id);

        if(patient==null){
            throw new BusinessException(messageSource.getMessage("patient.unknown", null, null));
        }

        patientRepository.delete(patient);
    }


    /**
     * Vérification des données obligatoires d'un patient
     *
     * @param patientDTO
     * @return Liste des erreurs
     */
    private List<String> checkPatientData(PatientDTO patientDTO){
        List<String> errors = new ArrayList<>();
        if(isEmpty(patientDTO.getName())){
            errors.add(messageSource.getMessage("patient.name.empty", null, null));
        }
        if(isEmpty(patientDTO.getFirstName())){
            errors.add(messageSource.getMessage("patient.firstname.empty", null, null));
        }
        if(isEmpty(patientDTO.getPhone())){
            errors.add(messageSource.getMessage("patient.phone.empty", null, null));
        }
        return errors;
    }

    private PatientDTO convertToDTO(Patient patient){
        return PatientDTO.builder()
                         .id(patient.getId())
                         .name(patient.getName())
                         .firstName(patient.getFirstName())
                         .phone(patient.getPhone())
                         .address(patient.getAddress())
                         .mail(patient.getMail())
                         .build();
    }

}
