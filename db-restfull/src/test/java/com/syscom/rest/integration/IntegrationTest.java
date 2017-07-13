package com.syscom.rest.integration;

import com.syscom.rest.integration.config.ConfigIntegration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ansible on 13/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigIntegration.class}) //webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
public class IntegrationTest {
}
