package com.syscom.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by ansible on 21/06/17.
 */
@EntityScan(basePackages = "com.syscom.domains")
public class RepositoryTestConfiguration {
}
