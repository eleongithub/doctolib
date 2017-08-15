package com.syscom.service;

import com.syscom.dao.RoleRepository;
import com.syscom.dao.UserRepository;
import com.syscom.domains.dto.UserDTO;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Role;
import com.syscom.service.exceptions.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static com.syscom.domains.enums.Role.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test des services métiers des utilisateurs {@link UserService}
 *
 * Created by Eric Legba on 01/07/17.
 */

public class UserServiceTest extends ServiceTest {

    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String LIBELLE = "LIBELLE";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullUserThrowException() throws Exception {
        userService.create(null);
    }

    @Test(expected = BusinessException.class)
    public void createUserWithEmptyDataThrowException() throws Exception {
        userService.create(new UserDTO());
    }


    @Test(expected = BusinessException.class)
    public void createUserWithExistLoginThrowException() throws Exception {
        Role role = Role.builder()
                        .libelle(LIBELLE)
                        .code(ADMIN.name())
                        .build();
        role = roleRepository.save(role);
        User user = User.builder()
                        .login(LOGIN)
                        .name(NAME)
                        .firstName(FIRST_NAME)
                        .password(PASSWORD)
                        .role(role)
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

    @Test(expected = BusinessException.class)
    public void createUserWithoutRoleThrowException() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                                 .login(LOGIN)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .build();
        userService.create(userDTO);
    }


    @Test(expected = BusinessException.class)
    public void createUserWithUnknownRoleThrowException() throws Exception {
        Role role = Role.builder()
                        .libelle(LIBELLE)
                        .code("role")
                        .build();
        roleRepository.save(role);
        UserDTO userDTO = UserDTO.builder()
                                 .login(LOGIN)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .role(ADMIN.name())
                                 .build();
        userService.create(userDTO);
    }

    @Test
    public void createUser() throws BusinessException {
        Role role = Role.builder()
                        .libelle(LIBELLE)
                        .code(ADMIN.name())
                        .build();
        roleRepository.save(role);
        UserDTO userDTO = UserDTO.builder()
                                 .login(LOGIN)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .role(ADMIN.name())
                                 .build();
        userService.create(userDTO);
    }


    @Test
    public void authenticateUser() throws BusinessException {
        Role role = Role.builder()
                        .libelle(LIBELLE)
                        .code(ADMIN.name())
                        .build();
        roleRepository.save(role);
        UserDTO userDTO = UserDTO.builder()
                                 .login(LOGIN)
                                 .name(NAME)
                                 .firstName(FIRST_NAME)
                                 .password(PASSWORD)
                                 .role(ADMIN.name())
                                 .build();
        userService.create(userDTO);
        String token = userService.authenticate(LOGIN);
        assertThat(token).isNotNull();
    }

}
