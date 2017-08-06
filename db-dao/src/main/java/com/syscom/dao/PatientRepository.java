package com.syscom.dao;

import com.syscom.domains.models.Patient;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour effectuer les CRUD des patients {@link Patient}
 *
 * @author Eric Legba
 *
 */
public interface PatientRepository extends CrudRepository<Patient, Long> {
}
