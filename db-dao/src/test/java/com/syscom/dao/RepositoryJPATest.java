package com.syscom.dao;

import com.syscom.dao.config.RepositoryTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ansible on 01/07/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
public abstract class RepositoryJPATest {
}
