package com.syscom.service;

import com.syscom.dao.UserRepository;
import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.models.User;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test des services métiers des utilisateurs {@link UserService}
 *
 * Created by ansible on 01/07/17.
 */

public class UserServiceTest extends ServiceTest {

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test(expected = IllegalArgumentException.class)
    public void createNullUserThrowException() throws BusinessException {
        userService.create(null);
    }

    @Test(expected = BusinessException.class)
    public void createUserWithEmptyDataThrowException() throws BusinessException {
        userService.create(new UserDTO());
    }


    @Test(expected = BusinessException.class)
    public void createUserWithExistLoginThrowException() throws BusinessException {
        User user = User.builder()
                .login(LOGIN)
                .name(NAME)
                .firstName(FIRST_NAME)
                .password(PASSWORD)
                .build();
        userRepository.save(user);

        UserDTO userDTO = UserDTO.builder()
                .login(LOGIN)
                .name("name1")
                .firstName("first_name_1")
                .password("password_1")
                .build();
        userService.create(userDTO);
    }


    @Test
    public void createUser() throws BusinessException {
        UserDTO userDTO = UserDTO.builder()
                                 .login(LOGIN)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .build();
        userService.create(userDTO);
    }
}
