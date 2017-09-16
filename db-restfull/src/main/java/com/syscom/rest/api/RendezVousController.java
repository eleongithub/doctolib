package com.syscom.rest.api;

import com.syscom.domains.config.JsonConfigDate;
import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.utils.Fonctionnalites;
import com.syscom.service.RendezVousService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.syscom.rest.utils.RestPreconditions.checkFound;

/**
 * API pour la gestion des rendez-vous des patients
 *
 * Created by Eric Legba on 11/08/17.
 */
@Api(value = RendezVousController.PATH)
@RestController
@RequestMapping(RendezVousController.PATH)
public class RendezVousController implements BaseController {

    public static final String PATH = "/api/secured/rendezvous";

    @Autowired
    private RendezVousService rendezVousService;

    /**
     * API pour créer un nouveau rendez-vous à un patient
     *
     * @param patientId identifiant du patient
     * @param dateBegin date de debut de rendez-vous
     * @param dateEnd date de fin de rendez-vous
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @Secured(Fonctionnalites.ROLE_CREER_RENDEZ_VOUS)
    @ApiOperation(value = "Créer un nouveau rendez-vous", notes = "Créer un nouveau rendez-vous")
    public void create(@ApiParam(value = "Identifiant du patient", required = true)@RequestParam Long patientId,
                       @ApiParam(value = "Date de debut du rendez-vous", required = true)@RequestParam @DateTimeFormat(pattern = JsonConfigDate.LOCAL_DATE_TIME_PATTERN) LocalDateTime dateBegin,
                       @ApiParam(value = "Date de fin du rendez-vous", required = true)@RequestParam @DateTimeFormat(pattern = JsonConfigDate.LOCAL_DATE_TIME_PATTERN) LocalDateTime dateEnd) throws BusinessException {
        checkFound(patientId);
        checkFound(dateBegin);
        checkFound(dateEnd);
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .patientId(patientId)
                                                   .dateBegin(dateBegin)
                                                   .dateEnd(dateEnd)
                                                   .build();
        rendezVousService.create(rendezVousDTO);
    }

    /**
     * API pour récuperer la liste des rendez-vous
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @Secured(Fonctionnalites.ROLE_CONSULTER_RENDEZ_VOUS)
    @ApiOperation(value = "Consulter les rendez-vous", notes = "Consulter les rendez-vous")
    public List<RendezVousDTO> findAll(){
        return rendezVousService.findAll();
    }


    /**
     * API pour rechercher un rendez-vous
     *
     * @param id Id du rendez-vous
     * @return
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @Secured(Fonctionnalites.ROLE_CONSULTER_RENDEZ_VOUS)
    @ApiOperation(value = "Consulter un rendez-vous", notes = "Consulter un rendez-vous")
    public RendezVousDTO findById(@PathVariable("id")Long id) throws BusinessException{
        checkFound(id);
        return rendezVousService.findById(id);
    }

    /**
     * API pour modifier le rendez-vous d'un patient
     *
     * @param id id du rendez-vous
     * @param patientId id du patient
     * @param dateBegin date de debut du rendez-vous
     * @param dateEnd date de fin du rendez-vous
     * @return le rendez-vous modifié {@link RendezVousDTO}
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @Secured(Fonctionnalites.ROLE_MODIFIER_RENDEZ_VOUS)
    @ApiOperation(value = "Modifier un rendez-vous", notes = "Modifier un rendez-vous")
    public RendezVousDTO update(@PathVariable("id")Long id,
                                @ApiParam(value = "ID du patient", required = true)@RequestParam Long patientId,
                                @ApiParam(value = "Date de debut du rendez-vous", required = true)@RequestParam @DateTimeFormat(pattern = JsonConfigDate.LOCAL_DATE_TIME_PATTERN) LocalDateTime dateBegin,
                                @ApiParam(value = "Date de fin du rendez-vous", required = true)@RequestParam @DateTimeFormat(pattern = JsonConfigDate.LOCAL_DATE_TIME_PATTERN) LocalDateTime dateEnd) throws BusinessException {

        checkFound(id);
        checkFound(patientId);
        checkFound(dateBegin);
        checkFound(dateEnd);
        RendezVousDTO rendezVousDTO = RendezVousDTO.builder()
                                                   .patientId(patientId)
                                                   .dateBegin(dateBegin)
                                                   .dateEnd(dateEnd)
                                                   .build();
        return rendezVousService.update(id,rendezVousDTO);
    }

    /**
     * API pour supprimer le rendez-vous d'un patient
     *
     * @param id du renvez-vous à supprimer
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @Secured(Fonctionnalites.ROLE_SUPPRIMER_RENDEZ_VOUS)
    @ApiOperation(value = "Supprimer un rendez-vous", notes = "Supprimer un rendez-vous")
    public void delete(@PathVariable("id")Long id) throws BusinessException {
        checkFound(id);
        rendezVousService.delete(id);
    }
}
