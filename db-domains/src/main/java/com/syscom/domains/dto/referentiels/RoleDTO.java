package com.syscom.domains.dto.referentiels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * Classe DTO d'un rôle.
 *
 * Created by Eric Legba on 02/08/17.
 */
@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
public class RoleDTO implements Serializable {

    private String code;
    private String libelle;

}
