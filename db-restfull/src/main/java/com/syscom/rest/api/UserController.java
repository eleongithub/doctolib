package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
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
    public void createUser(@RequestBody UserDTO userDTO) throws BusinessException {
        RestPreconditions.checkFound(userDTO);
        userService.create(userDTO);
    }

}
