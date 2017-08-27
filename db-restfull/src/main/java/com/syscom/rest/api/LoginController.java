package com.syscom.rest.api;

import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API pour l'authentification des utilisateurs
 *
 * Created by Eric Legba on 30/07/17.
 */
@Api(value = UserController.PATH)
@RestController
@RequestMapping(LoginController.PATH)
public class LoginController implements BaseController {

    public static final String PATH = "/api/login";

    @Autowired
    private UserService userService;

    /**
     * API pour authentifier un utilisateur et retourner un jeton de sécurité
     *
     * @return token jeton d'authentification à utiliser pour utiliser les APIs sécurisés
     * @throws BusinessException Exception fonctionnelle {@link BusinessException}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "API pour l'authentification des utilisateurs", notes = "Authentification d'un utilisateur")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")})
    public String login() throws BusinessException {
//        Récupérer le login de l'utilisateur à partir du context Spring Security
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.authenticate(user.getUsername());
    }

}
