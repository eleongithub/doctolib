package com.syscom.service;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.service.exceptions.BusinessException;

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
}
