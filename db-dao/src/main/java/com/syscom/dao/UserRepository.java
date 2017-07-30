package com.syscom.dao;

import com.syscom.domains.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repository pour effectuer des CRUD des utilisateurs {@link User}
 *
 * @author el1638en
 * @since 09/06/17 17:17
 */
public interface UserRepository extends CrudRepository<User, Long>{

	@Query(name = "existsByLogin", value = "SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.login = :login")
	boolean existsByLogin(@Param("login") String login);

	User findByLogin(String login);

	User findById(Long id);
}
