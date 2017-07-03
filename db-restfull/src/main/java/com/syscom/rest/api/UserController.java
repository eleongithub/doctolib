package com.syscom.rest.api;

import com.syscom.domains.dto.UserDTO;
import com.syscom.rest.utils.RestPreconditions;
import com.syscom.service.UserService;
import com.syscom.service.exceptions.BusinessException;
import io.swagger.annotations.*;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Web service for User
 * Created by ansible on 02/07/17.
 */
@Api(description = "Operations about user.")
public class UserController {

    public static final String ROOT= "/user";

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@ApiParam(required = true, value = "user to create") @RequestBody UserDTO userDTO) throws BusinessException {
        RestPreconditions.checkFound(userDTO);
        userService.create(userDTO);
    }

}
