package com.syscom.rest.api;

import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API pour l'authentification des utilisateurs
 *
 * Created by Eric Legba on 30/07/17.
 */

@Api(value = UserController.PATH, description = "API pour l'authentification des utilisateurs")
@RestController
@RequestMapping(LoginController.PATH)
public class LoginController implements BaseController {

    public static final String PATH = "/api/login";

    @Autowired
    private UserService userService;


    /**
     * API pour authentifier un utilisateur
     *
     * @param authotization
     * @return token
     * @throws BusinessException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Authentification d'un utilisateur", notes = "Authentification d'un utilisateur")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
    })
    public String login(@ApiParam(value = "authorization(Login:MDP(Base 64))", required = true)@RequestBody String authotization) throws BusinessException {
        RestPreconditions.checkFound(authotization);
        return userService.authenticate(authotization);
    }

}
