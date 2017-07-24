package com.syscom.domains.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * Clase DTO représentant les données d'un utilisateur.
 *
 * Created by ansible on 01/07/17.
 */

@Data
@Builder
@ToString(exclude = {"password"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@ApiModel(value="UserDTO", description="User model")
public class UserDTO extends BaseDTO {


    private String name;

    private String firstName;

    private String login;

    private String password;
}
