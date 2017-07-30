package com.syscom.dao;

import com.syscom.domains.models.referentiels.Role;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository pour effectuer des CRUD des rôles {@link Role}
 *
 * @author el1638en
 * @since 27/07/17 21:23
 */
public interface RoleRepository extends CrudRepository<Role, Long>{

    Role findByCode(String code);
}
