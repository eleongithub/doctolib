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

import java.time.LocalDateTime;
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

    private static final String RDV_ID_NOT_NULL = "rdv.id.not.null";
    private static final String FIRST_NAME = "firstname";
    private static final String UNKNOWN_RDV_ID = "rdv.unknown";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailService mailService;


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
        if(patient!=null && !isEmpty(patient.getMail())){
            Map<String,Object> datas = new HashMap<>();
            datas.put("name",patient.getName());
            datas.put(FIRST_NAME,patient.getFirstName());
            String formatDateTime = rendezVousDTO.getDateBegin().format(FORMATTER);
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
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RendezVousDTO findById(Long id) throws BusinessException {
        Assert.notNull(id, messageService.getMessage(RDV_ID_NOT_NULL));
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        if(rendezVous==null){
            throw new BusinessException(messageService.getMessage(UNKNOWN_RDV_ID));
        }
        return convertToDTO(rendezVous);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RendezVousDTO update(Long id, RendezVousDTO rendezVousDTO) throws BusinessException {
//      1 - Vérifier que l'ID et le rendez-vous sont non nuls.
        Assert.notNull(id, messageService.getMessage(RDV_ID_NOT_NULL));
        Assert.notNull(rendezVousDTO, messageService.getMessage("rdv.not.null"));

//      2 - Vérifier que le rendez-vous existe déjà en BDD
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageService.getMessage(UNKNOWN_RDV_ID));

        Patient patient = null;
        if(rendezVousDTO.getPatientId()!=null){
            patient = patientRepository.findOne(rendezVousDTO.getPatientId());
        }

//		3 - Verifier la cohérence des données
        List<String> errors = checkDatas(rendezVousDTO, patient);
        if(!errors.isEmpty()){
            throw new BusinessException(StringUtils.join(errors," "));
        }
        LocalDateTime odlDate = rendezVous.getDateBegin();
        rendezVous.setDateBegin(rendezVousDTO.getDateBegin());
        rendezVous.setDateEnd(rendezVousDTO.getDateEnd());
        rendezVous.setPatient(patient);
        rendezVousRepository.save(rendezVous);

//      4 - Envoyer le mail de modification du rendez-vous au patient
        if(patient!=null && !isEmpty(patient.getMail())) {
            Map<String, Object> datas = new HashMap<>();
            datas.put("name", patient.getName());
            datas.put(FIRST_NAME, patient.getFirstName());
            String oldDateTime = odlDate.format(FORMATTER);
            String newDateTime = rendezVousDTO.getDateBegin().format(FORMATTER);
            datas.put("oldDateRdv", oldDateTime);
            datas.put("newDateRdv", newDateTime);
            String subject = messageService.getMessage("patient.rdv.update.mail.subject");
            MailDTO mailDTO = MailDTO.builder()
                    .to(patient.getMail())
                    .subject(subject)
                    .datas(datas)
                    .template("update-rdv")
                    .build();
            mailService.sendMessage(mailDTO);
        }
        return convertToDTO(rendezVous);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws BusinessException {
//      1 - Vérifier que l'ID est non nul.
        Assert.notNull(id, messageService.getMessage(RDV_ID_NOT_NULL));

//      2 - Vérifier que le rendez-vous existe
        RendezVous rendezVous = rendezVousRepository.findOne(id);
        Assert.notNull(rendezVous, messageService.getMessage(UNKNOWN_RDV_ID));

        Patient patient = rendezVous.getPatient();
        LocalDateTime dateRdv = rendezVous.getDateBegin();
//      3 - Supprimer le rendez-vous
        rendezVousRepository.delete(id);

//      4 - Envoyer un mail d'annulation du rendez-vous
        if(patient!=null && !isEmpty(patient.getMail())) {
            Map<String, Object> datas = new HashMap<>();
            datas.put("name", patient.getName());
            datas.put(FIRST_NAME, patient.getFirstName());
            String dateTime = dateRdv.format(FORMATTER);
            datas.put("dateRdv", dateTime);
            String subject = messageService.getMessage("patient.rdv.cancel.mail.subject");
            MailDTO mailDTO = MailDTO.builder()
                    .to(patient.getMail())
                    .subject(subject)
                    .datas(datas)
                    .template("delete-rdv")
                    .build();
            mailService.sendMessage(mailDTO);
        }
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

        if(rendezVousDTO.getDateBegin()!=null && rendezVousDTO.getDateEnd()!=null
                && rendezVousDTO.getDateBegin().isAfter(rendezVousDTO.getDateEnd())){
                errors.add(messageService.getMessage("rdv.date_begin.before.date_end.empty"));
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
                            .id(rendezVous.getId())
                            .dateBegin(rendezVous.getDateBegin())
                            .dateEnd(rendezVous.getDateEnd())
                            .patientId(rendezVous.getPatient().getId())
                            .build();
    }
}
