package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
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
 * Rest Web service pour les utilisateur
 *
 * Created by ansible on 02/07/17.
 */
@Api(value = UserController.ROOT, description = "Operations about user")
@RestController
@RequestMapping(UserController.ROOT)
public class UserController implements BaseController {

    public static final String ROOT= "/user";

    @Autowired
    private UserService userService;

    /**
     * API pour créer un nouvel utilisateur
     *
     * @param userDTO {@link UserDTO}
     * @throws BusinessException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Créer un nouvel utilisateur", notes = "Créer un nouvel utilisateur")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request param error")
                          })
    public void createUser(@ApiParam(value = "User to create", required = true)@RequestBody UserDTO userDTO) throws BusinessException {
        RestPreconditions.checkFound(userDTO);
        userService.create(userDTO);
    }

}
