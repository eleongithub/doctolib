package com.syscom.service;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.service.exceptions.BusinessException;

import java.util.List;

/**
 * Contrat d'interface des services métiers des patients {@link com.syscom.domains.models.Patient}
 * Created by Eric LEGBA on 01/08/17.
 */
public interface PatientService {

    /**
     * Ajouter un nouveau patient
     *
     * @param patientDTO patient à ajouter
     * @throws BusinessException
     */
    void create (PatientDTO patientDTO) throws BusinessException;

    /**
     * Rechercher un patient
     *
     * @param id identifiant du patient
     * @throws BusinessException
     */
    PatientDTO findById (Long id) throws BusinessException;


    /**
     * Rechercher tous les patients
     *
     */
    List<PatientDTO> findAll();


    /**
     * Modifier les informations d'un patient
     *
     * @param id identifiant du patient
     * @param patientDTO patient à modifier
     * @throws BusinessException
     */
    PatientDTO update (Long id, PatientDTO patientDTO) throws BusinessException;

    /**
     * Supprimer un patient
     *
     * @param id identifiant du patient
     * @throws BusinessException
     */
    void delete (Long id) throws BusinessException;
}
