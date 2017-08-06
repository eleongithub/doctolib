package com.syscom.rest.api;

import com.syscom.domains.dto.PatientDTO;
import com.syscom.domains.utils.Fonctionnalites;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.PatientService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API pour la gestion des patients
 *
 * Created by Eric Legba on 01/08/17.
 */
@Api(value = PatientController.PATH, description = "API pour la gestion des patients")
@RestController
@RequestMapping(PatientController.PATH)
public class PatientController implements BaseController {

    public static final String PATH = "/api/secured/patient";

    @Autowired
    private PatientService patientService;

    /**
     * API pour ajouter un nouveau patient
     *
     * @param patientDTO {@link PatientDTO}
     * @throws Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @Secured(Fonctionnalites.ROLE_F_AJOUTER_PATIENT)
    @ApiOperation(value = "Ajouter un nouveau patient", notes = "Ajouter un nouveau patient")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
    })
    public void createUser(@ApiParam(value = "Patient à ajouter", required = true)@RequestBody PatientDTO patientDTO) throws BusinessException {
        RestPreconditions.checkFound(patientDTO);
        patientService.create(patientDTO);
    }

}
