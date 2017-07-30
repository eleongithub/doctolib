package com.syscom.domains.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Eric Legba on 27/07/17.
 */

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@ApiModel(value="TokenDTO", description="Token d'authentification des utilisateurs")
public class TokenDTO implements Serializable {

    private String value;

    private LocalDateTime dateExpiration;

    private Long userId;

}
