package com.syscom.dao;

import com.syscom.domains.models.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * Repository pour effectuer des CRUD des tokens {@link com.syscom.domains.models.Token}
 *
 * @author el1638en
 * @since 27/07/17 21:23
 */
public interface TokenRepository extends CrudRepository<Token, Long>{

    Token findByValue(String value);

    @Query(name = "findByuserId", value = "SELECT t FROM Token t WHERE t.user.id = :id")
    Token findByuserId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(name = "deleteExpiredToken", value = "DELETE FROM Token t WHERE t.dateExpiration < :localDateTime")
    void deleteExpiredToken(@Param("localDateTime")LocalDateTime localDateTime);

}
