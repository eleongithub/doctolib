package com.syscom.service.impl;

import com.syscom.dao.PatientRepository;
import com.syscom.dao.RendezVousRepository;
import com.syscom.domains.dto.MailDTO;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.models.Patient;
import com.syscom.domains.models.RendezVous;
import com.syscom.service.MailService;
import com.syscom.service.MessageService;
import com.syscom.service.RendezVousService;
import com.syscom.service.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Implémentation des services métiers des rendez-vous {@link com.syscom.domains.models.RendezVous}
 *
 * Created by Eric Legba on 11/08/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RendezVousServiceImpl implements RendezVousService{

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailService mailService;

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
        Assert.notNull(rendezVousDTO,messageService.getMessage("rdv.not.null"));

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
        if(!isEmpty(patient.getMail())){
            Map<String,Object> datas = new HashMap<>();
            datas.put("name",patient.getName());
            datas.put("firstname",patient.getFirstName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatDateTime = rendezVousDTO.getDateBegin().format(formatter);
            datas.put("dateRdv",formatDateTime);
            String subject = messageService.getMessage("patient.rdv.mail.subject");
            MailDTO mailDTO = MailDTO.builder()
                    .to(patient.getMail())
                    .subject(subject)
                    .datas(datas)
                    .template("create-rdv")
                    .build();
            mailService.sendMessage(mailDTO);
        }
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
        Assert.notNull(id, messageService.getMessage("rdv.id.not.null"));
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        if(rendezVous==null){
            throw new BusinessException(messageService.getMessage("rdv.unknown"));
        }
        return convertToDTO(rendezVous);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RendezVousDTO update(Long id, RendezVousDTO rendezVousDTO) throws BusinessException {
//      1 - Vérifier que l'ID et le rendez-vous sont non nuls.
        Assert.notNull(id, messageService.getMessage("rdv.id.not.null"));
        Assert.notNull(rendezVousDTO, messageService.getMessage("rdv.not.null"));

//      2 - Vérifier que le rendez-vous existe déjà en BDD
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageService.getMessage("rdv.id.not.null"));

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
        Assert.notNull(id, messageService.getMessage("rdv.id.not.null"));

//      2 - Vérifier que le rendez-vous existe
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageService.getMessage("rdv.id.not.null"));

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
            errors.add(messageService.getMessage("patient.not.null"));
        }

        if(rendezVousDTO.getDateBegin()==null){
            errors.add(messageService.getMessage("rdv.date.begin.empty"));
        }

        if(rendezVousDTO.getDateEnd()==null){
            errors.add(messageService.getMessage("rdv.date.end.empty"));
        }

        if(rendezVousDTO.getDateBegin()!=null && rendezVousDTO.getDateEnd()!=null){
            if(rendezVousDTO.getDateBegin().isAfter(rendezVousDTO.getDateEnd())){
                errors.add(messageService.getMessage("rdv.date_begin.before.date_end.empty"));
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
