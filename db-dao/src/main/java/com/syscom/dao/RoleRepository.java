package com.syscom.dao;

import com.syscom.domains.models.referentiels.Role;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository pour effectuer des CRUD des rôles {@link Role}
 *
 * @author Eric Legba
 * @since 27/07/17 21:23
 */
public interface RoleRepository extends CrudRepository<Role, Long>{

    /**
     * Rechercher un rôle à partir de son code
     *
     * @param code code du rôle
     * @return un rôle {@link Role}
     */
    Role findByCode(String code);
}
