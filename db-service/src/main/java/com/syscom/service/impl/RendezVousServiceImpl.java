package com.syscom.service.impl;

import com.syscom.dao.PatientRepository;
import com.syscom.dao.RendezVousRepository;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.models.Patient;
import com.syscom.domains.models.RendezVous;
import com.syscom.service.RendezVousService;
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

/**
 * Implémentation des services métiers des rendez-vous {@link com.syscom.domains.models.RendezVous}
 *
 * Created by Eric Legba on 11/08/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RendezVousServiceImpl implements RendezVousService{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(RendezVousDTO rendezVousDTO) throws BusinessException {
//      1 - Vérifier que l'objet est non nul.
        Assert.notNull(rendezVousDTO,messageSource.getMessage("rdv.not.null", null, null));

        Patient patient = null;
        if(rendezVousDTO.getPatientId()!=null){
            patient = patientRepository.findOne(rendezVousDTO.getPatientId());
        }

//		2 - Verifier la cohérence des données
        List<String> errors = checkDatas(rendezVousDTO, patient);
        if(!errors.isEmpty()){
            throw new BusinessException(StringUtils.join(errors," "));
        }

//      3 - Enregistrer le rendez-vous
        RendezVous rendezVous = RendezVous.builder()
                                          .dateBegin(rendezVousDTO.getDateBegin())
                                          .dateEnd(rendezVousDTO.getDateEnd())
                                          .patient(patient)
                                          .build();
        rendezVousRepository.save(rendezVous);

//      4 - Envoyer le mail de confirmation au patient
//      TODO - Envoyer le mail
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RendezVousDTO> findAll() {
        Iterable<RendezVous> rdvs = rendezVousRepository.findAll();
        return StreamSupport.stream(rdvs.spliterator(), false)
                            .map(rendezVous -> convertToDTO(rendezVous))
                            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RendezVousDTO findById(Long id) throws BusinessException {
        Assert.notNull(id, messageSource.getMessage("rdv.id.not.null", null, null));
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        if(rendezVous==null){
            throw new BusinessException(messageSource.getMessage("rdv.unknown", null, null));
        }
        return convertToDTO(rendezVous);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RendezVousDTO update(Long id, RendezVousDTO rendezVousDTO) throws BusinessException {
//      1 - Vérifier que l'ID et le rendez-vous sont non nuls.
        Assert.notNull(id, messageSource.getMessage("rdv.id.not.null", null, null));
        Assert.notNull(rendezVousDTO, messageSource.getMessage("rdv.not.null", null, null));

//      2 - Vérifier que le rendez-vous existe déjà en BDD
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageSource.getMessage("rdv.id.not.null", null, null));

        Patient patient = null;
        if(rendezVousDTO.getPatientId()!=null){
            patient = patientRepository.findOne(rendezVousDTO.getPatientId());
        }

//		3 - Verifier la cohérence des données
        List<String> errors = checkDatas(rendezVousDTO, patient);
        if(!errors.isEmpty()){
            throw new BusinessException(StringUtils.join(errors," "));
        }

        rendezVous.setDateBegin(rendezVousDTO.getDateBegin());
        rendezVous.setDateEnd(rendezVousDTO.getDateEnd());
        rendezVous.setPatient(patient);
        rendezVousRepository.save(rendezVous);

//      4 - Envoyer le mail de modification du rendez-vous au patient
//      TODO - Envoyer le mail
        return convertToDTO(rendezVous);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws BusinessException {
//      1 - Vérifier que l'ID est non nul.
        Assert.notNull(id, messageSource.getMessage("rdv.id.not.null", null, null));

//      2 - Vérifier que le rendez-vous existe
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageSource.getMessage("rdv.id.not.null", null, null));

//      3 - Supprimer le rendez-vous
        rendezVousRepository.delete(id);

//      4 - Envoyer un mail d'annulation du rendez-vous
//      TODO - Envoyer le mail
    }

    /**
     * Méthode pour vérifier la cohérence des données du rendez-vous.
     *
     * @param rendezVousDTO Données DTO du rendez-vous {@link RendezVousDTO}
     * @return Liste de message d'erreurs
     */
    private List<String> checkDatas(RendezVousDTO rendezVousDTO, Patient patient){
        List<String> errors = new ArrayList<>();
        if(patient==null){
            errors.add(messageSource.getMessage("patient.not.null", null, null));
        }

        if(rendezVousDTO.getDateBegin()==null){
            errors.add(messageSource.getMessage("rdv.date.begin.empty", null, null));
        }

        if(rendezVousDTO.getDateEnd()==null){
            errors.add(messageSource.getMessage("rdv.date.end.empty", null, null));
        }

        if(rendezVousDTO.getDateBegin()!=null && rendezVousDTO.getDateEnd()!=null){
            if(rendezVousDTO.getDateBegin().isAfter(rendezVousDTO.getDateEnd())){
                errors.add(messageSource.getMessage("rdv.date_begin.before.date_end.empty", null, null));
            }
        }
        return errors;
    }


    /**
     * Convertir un objet relationnel en DTO
     *
     * @param rendezVous rendez-vous {@link RendezVous}
     * @return Objet DTO d'un rendez-vous {@link RendezVousDTO}
     */
    private RendezVousDTO convertToDTO(RendezVous rendezVous){
        return RendezVousDTO.builder()
                            .dateBegin(rendezVous.getDateBegin())
                            .dateEnd(rendezVous.getDateEnd())
                            .patientId(rendezVous.getPatient().getId())
                            .build();
    }
}
