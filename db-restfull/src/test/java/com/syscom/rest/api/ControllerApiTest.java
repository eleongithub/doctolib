package com.syscom.rest.api;

import com.syscom.dao.RoleRepository;
import com.syscom.domains.models.referentiels.Role;
import com.syscom.rest.Application;
import com.syscom.rest.exception.handler.DbEntityExceptionHandler;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.syscom.domains.enums.Role.ADMIN;

/**
 * Ressource utilisé : https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 *
 * Created by Eric Legba on 03/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class ControllerApiTest {

    private static final String ROLE_LIBELLE = "LIBELLE";
    protected static final String USER_LOGIN = "LOGIN";
    protected static final String USER_NAME = "NAME";
    protected static final String USER_FIRST_NAME = "FIRST_NAME";
    protected static final String USER_PASSWORD = "PASSWORD";

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    /**
     * Initialisation du Mock MVC qui sera utilisé pour tester les contrôleurs des APIs.
     *
     * @param controller
     * @throws Exception
     */
    public void initMockMvc(BaseController controller) throws Exception {
        this.mockMvc = MockMvcBuilders
                                      .standaloneSetup(controller).setControllerAdvice(new DbEntityExceptionHandler())
                                      .addFilters(springSecurityFilterChain)
//                                      .apply(SecurityMockMv)
                                      .build();

    }

    /**
     * Méthode pour convertir un objet Java au format Json
     * @param object
     * @return
     * @throws IOException
     */
    protected String json(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected Role createAdminRole(){
        com.syscom.domains.models.referentiels.Role role = com.syscom.domains.models.referentiels.Role.builder()
                .libelle(ROLE_LIBELLE)
                .code(ADMIN.name())
                .build();
        return roleRepository.save(role);
    }

    protected String builHeaderCredentials(String login, String password){
        return "Basic " + Base64Utils.encodeToString((login+":"+password).getBytes());
    }
}
