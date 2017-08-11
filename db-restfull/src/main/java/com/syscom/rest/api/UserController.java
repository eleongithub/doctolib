package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API pour la création des comptes d'utilisateur
 *
 * Created by Eric Legba on 02/07/17.
 */
@Api(value = UserController.PATH, description = "Création d'un compte utilisateur")
@RestController
@RequestMapping(UserController.PATH)
public class UserController implements BaseController {

    public static final String PATH = "/api/user";

    @Autowired
    private UserService userService;

    /**
     * API pour créer un nouvel utilisateur
     *
     * @param userDTO {@link UserDTO}
     * @throws Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Créer un nouvel utilisateur", notes = "Créer un nouvel utilisateur")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
                          })
    public void createUser(@ApiParam(value = "Utilisateur à créer", required = true)@RequestBody UserDTO userDTO) throws BusinessException {
        RestPreconditions.checkFound(userDTO);
        userService.create(userDTO);
    }

}
