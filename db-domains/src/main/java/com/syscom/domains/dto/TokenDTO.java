package com.syscom.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * Classe DTO des jetons d'authentification.
 *
 * Created by Eric Legba on 27/07/17.
 */

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
public class TokenDTO implements Serializable {

    private String value;

    private LocalDateTime dateExpiration;

    private Long userId;

}
