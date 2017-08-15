package com.syscom.rest.api;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.domains.utils.Fonctionnalites;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.PatientService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * API pour la gestion des patients
 *
 * Created by Eric Legba on 01/08/17.
 */
@Api(value = PatientController.PATH)
@RestController
@RequestMapping(PatientController.PATH)
public class PatientController implements BaseController {

    public static final String PATH = "/api/secured/patient";

    @Autowired
    private PatientService patientService;

    /**
     * API pour ajouter un nouveau patient
     *
     * @param patientDTO paitent à ajouter {@link PatientDTO}
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @Secured(Fonctionnalites.ROLE_AJOUTER_PATIENT)
    @ApiOperation(value = "Ajouter un nouveau patient", notes = "Ajouter un nouveau patient")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
    })
    public void create(@ApiParam(value = "Patient à ajouter", required = true)@RequestBody PatientDTO patientDTO) throws BusinessException {
        RestPreconditions.checkFound(patientDTO);
        patientService.create(patientDTO);
    }

    /**
     * API pour consulter la liste des patients
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @Secured(Fonctionnalites.ROLE_CONSULTER_PATIENT)
    @ApiOperation(value = "Consulter les patients", notes = "Consulter les patients")
    public List<PatientDTO> findAll(){
        return patientService.findAll();
    }


    /**
     * API pour rechercher un patient
     * @param id Id du patient
     * @return PatientDTO patient recherché {@link PatientDTO}
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @Secured(Fonctionnalites.ROLE_CONSULTER_PATIENT)
    @ApiOperation(value = "Consulter un patient", notes = "Consulter un patient")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")})
    public PatientDTO findById(@PathVariable("id")Long id) throws BusinessException{
        RestPreconditions.checkFound(id);
        return patientService.findById(id);
    }


    /**
    * API pour modifier les données d'un patient
    *
    * @param id Id du patient
    * @param patientDTO {@link PatientDTO}
    * @throws BusinessException Exception fonctionnelle {@link BusinessException}
    */
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @Secured(Fonctionnalites.ROLE_MODIFIER_PATIENT)
    @ApiOperation(value = "Modifier un patient", notes = "Modifier un patient")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")})
    public PatientDTO update(@PathVariable("id")Long id,@RequestBody PatientDTO patientDTO) throws BusinessException {
        RestPreconditions.checkFound(id);
        RestPreconditions.checkFound(patientDTO);
        return patientService.update(id,patientDTO);
    }


    /**
     * API pour Supprimer un patient
     *
     * @param id Id du patient
     * @throws Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @Secured(Fonctionnalites.ROLE_SUPPRIMER_PATIENT)
    @ApiOperation(value = "Supprimer un patient", notes = "Supprimer un patient")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error") })
    public void delete(@PathVariable("id")Long id) throws BusinessException {
        RestPreconditions.checkFound(id);
        patientService.delete(id);
    }
}
