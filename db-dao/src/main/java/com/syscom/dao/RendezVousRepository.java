package com.syscom.dao;

import com.syscom.domains.models.RendezVous;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour effectuer les CRUD des rendez-vous {@link com.syscom.domains.models.RendezVous}
 *
 * Created by Eric Legba on 11/08/17.
 * @author Eric Legba
 */
public interface RendezVousRepository extends CrudRepository<RendezVous, Long> {
}
