package com.syscom.service;

import com.syscom.service.config.ServiceTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ansible on 01/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceTestConfiguration.class})
@DataJpaTest
public abstract class AbstractServiceTest {
}
