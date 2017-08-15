package com.syscom.service.impl;

import com.syscom.dao.PatientRepository;
import com.syscom.domains.dto.PatientDTO;
import com.syscom.domains.models.Patient;
import com.syscom.service.MessageService;
import com.syscom.service.PatientService;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MessageService messageService;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(PatientDTO patientDTO) throws BusinessException {
        Assert.notNull(patientDTO, messageService.getMessage("patient.not.null"));
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientDTO findById(Long id) throws BusinessException {
        Assert.notNull(id, messageService.getMessage("patient.id.not.null"));
        Patient patient = patientRepository.findOne(id);
        if(patient==null){
            throw new BusinessException(messageService.getMessage("patient.unknown"));
        }
        return convertToDTO(patient);
    }

    /**
     * {@inheritDoc}
     */
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
        Assert.notNull(id, messageService.getMessage("patient.id.not.null"));
        Assert.notNull(patientDTO, messageService.getMessage("patient.not.null"));
        Patient patient = patientRepository.findOne(id);

        if(patient==null || patient.getId() != patientDTO.getId()){
            throw new BusinessException(messageService.getMessage("patient.unknown"));
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
        Assert.notNull(id, messageService.getMessage("patient.id.not.null"));
        Patient patient = patientRepository.findOne(id);
        if(patient==null){
            throw new BusinessException(messageService.getMessage("patient.unknown"));
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
            errors.add(messageService.getMessage("patient.name.empty"));
        }
        if(isEmpty(patientDTO.getFirstName())){
            errors.add(messageService.getMessage("patient.firstname.empty"));
        }
        if(isEmpty(patientDTO.getPhone())){
            errors.add(messageService.getMessage("patient.phone.empty"));
        }
        return errors;
    }

    /**
     * Créer un objet DTO de patient
     *
     * @param patient {@link Patient}
     * @return patientDTO {@link PatientDTO}
     */
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
