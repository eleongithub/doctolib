package com.syscom.dao;

import com.syscom.domains.models.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Repository pour effectuer des CRUD des tokens {@link com.syscom.domains.models.Token}
 *
 * @author Eric Legba
 * @since 27/07/17 21:23
 */
public interface TokenRepository extends CrudRepository<Token, Long>{

    /**
     * Rechercher un jeton d'authentification par sa valeur.
     *
     * @param value
     * @return un jeton d'authentification {@link Token}
     */
     Token findByValue(@Param("value")String value);

    /**
     * Rechercher les jetons d'authentification d'un utilisateur
     *
     * @param userId
     * @return liste de jetons {@link Token}
     */
    List<Token> findByUser_Id(Long userId);

    /**
     * Supprimer un jeton qui est expirée
     *
     * @param localDateTime
     */
    @Transactional
    @Modifying
    @Query(name = "deleteExpiredToken", value = "DELETE FROM Token t WHERE t.dateExpiration < :localDateTime")
    void deleteExpiredToken(@Param("localDateTime")LocalDateTime localDateTime);

}
