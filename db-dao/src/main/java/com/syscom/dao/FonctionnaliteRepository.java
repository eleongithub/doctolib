package com.syscom.dao;

import com.syscom.domains.models.referentiels.Fonctionnalite;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour effectuer les CRUD des fonctionnlaites {@link Fonctionnalite}
 * Created by Eric Legba on 13/08/17.
 * @author Eric Legba
 */
public interface FonctionnaliteRepository extends CrudRepository<Fonctionnalite, Long> {
}
