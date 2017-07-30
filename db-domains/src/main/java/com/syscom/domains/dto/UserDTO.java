package com.syscom.domains.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Clase DTO représentant les données d'un utilisateur {@link com.syscom.domains.models.User}.
 *
 * Created by Eric Legba on 01/07/17.
 */

@Data
@Builder
@ToString(exclude = {"password"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@ApiModel(value="UserDTO", description="Données des utilisateurs")
public class UserDTO implements Serializable {


    private String name;

    private String firstName;

    private String login;

    private String password;

    @ApiModelProperty(value = "ADMIN, ASSISTANTE_DIRECTION")
    private String role;
}
