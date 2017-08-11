package com.syscom.service;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.service.exceptions.BusinessException;

import java.util.List;

/**
 * Contrat d'interface des service métiers des rendez-vous {@link com.syscom.domains.models.RendezVous}
 *
 * Created by Eric Legba on 11/08/17.
 */
public interface RendezVousService {

    /**
     * Créer un nouveau rendez-vous
     *
     * @param rendezVousDTO nouveau rendez-vous à enregistrer
     * @throws BusinessException  Exception fonctionnelle {@link BusinessException}
     */
    void create(RendezVousDTO rendezVousDTO) throws BusinessException;

    /**
     * Rechercher tous les rendez-vous
     *
     */
    List<RendezVousDTO> findAll();


    /**
     * Rechercher un rendez-vous
     *
     * @param id identifiant du rendez-vous
     * @throws BusinessException
     */
    RendezVousDTO findById (Long id) throws BusinessException;

    /**
     * Modifier un rendez-vous
     *
     * @param id identifiant du rendez-vous
     * @param rendezVousDTO rendez-vous à modifier
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    RendezVousDTO update (Long id, RendezVousDTO rendezVousDTO) throws BusinessException;

    /**
     * Supprimer un rendez-vous
     *
     * @param id identifiant du rendez-vous
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    void delete (Long id) throws BusinessException;
}
