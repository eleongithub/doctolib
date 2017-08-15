package com.syscom.rest.api;

import com.syscom.domains.dto.RendezVousDTO;
import com.syscom.domains.utils.Fonctionnalites;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.RendezVousService;
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
 * API pour la gestion des rendez-vous des patients
 *
 * Created by Eric Legba on 11/08/17.
 */
@Api(value = RendezVousController.PATH)
@RestController
@RequestMapping(RendezVousController.PATH)
public class RendezVousController implements BaseController {

    public static final String PATH = "/api/secured/rendez_vous";

    @Autowired
    private RendezVousService rendezVousService;

    /**
     * API pour créer un nouveau rendez-vous à un patient
     *
     * @param rendezVousDTO {@link RendezVousDTO}
     * @throws Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @Secured(Fonctionnalites.ROLE_CREER_RENDEZ_VOUS)
    @ApiOperation(value = "Créer un nouveau rendez-vous", notes = "Créer un nouveau rendez-vous")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
    })
    public void create(@ApiParam(value = "Rendez-vous", required = true)@RequestBody RendezVousDTO rendezVousDTO) throws BusinessException {
        RestPreconditions.checkFound(rendezVousDTO);
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
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")})
    public RendezVousDTO findById(@PathVariable("id")Long id) throws BusinessException{
        RestPreconditions.checkFound(id);
        return rendezVousService.findById(id);
    }

    /**
     * API pour modifier le rendez-vous d'un patient
     *
     * @param id id du rendez-vous
     * @param rendezVousDTO objet DTO contenant les nouvelles informations du rendez-vous
     * @return le rendez-vous modifié {@link RendezVousDTO}
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @Secured(Fonctionnalites.ROLE_MODIFIER_RENDEZ_VOUS)
    @ApiOperation(value = "Modifier un rendez-vous", notes = "Modifier un rendez-vous")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")})
    public RendezVousDTO update(@PathVariable("id")Long id,@RequestBody RendezVousDTO  rendezVousDTO) throws BusinessException {
        RestPreconditions.checkFound(id);
        RestPreconditions.checkFound(rendezVousDTO);
        return rendezVousService.update(id,rendezVousDTO);
    }

    /**
     * API pour supprimer le rendez-vous d'un patient
     *
     * @param id du renvez-vous à supprimer
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @Secured(Fonctionnalites.ROLE_SUPPRIMER_PATIENT)
    @ApiOperation(value = "Supprimer un rendez-vous", notes = "Supprimer un rendez-vous")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error") })
    public void delete(@PathVariable("id")Long id) throws BusinessException {
        RestPreconditions.checkFound(id);
        rendezVousService.delete(id);
    }
}
