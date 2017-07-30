package com.syscom.service;

import com.syscom.domains.dto.TokenDTO;
import com.syscom.domains.models.Token;
import com.syscom.service.exceptions.BusinessException;

/**
 * Contrat d'interface des service métiers des jetons d'authentification {@link com.syscom.domains.models.Token}
 *
 * Created by Eric Legba on 27/07/17.
 */
public interface TokenService {

    /**
     *
     * Créer un nouveau jeton d'authentification
     *
     * @param tokenDTO jeton d'authentification {@link TokenDTO}
     */
    TokenDTO create (TokenDTO tokenDTO) throws BusinessException;


    /**
     * Rechercher un jeton d'authentification en cours de validité à partir de sa valeur
     *
     * @param value valeur du jeton d'authentification
     * @return jeton d'authentification {@link Token}
     */
    Token findValidTokenByValue(String value);
}
