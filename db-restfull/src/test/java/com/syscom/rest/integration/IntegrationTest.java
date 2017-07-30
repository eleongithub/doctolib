package com.syscom.rest.integration;

import com.syscom.rest.integration.config.ConfigIntegration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Eric Legba on 13/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ConfigIntegration.class})
public abstract class IntegrationTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;
}
