package com.syscom.domains.dto;

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
public class UserDTO extends BaseDTO {


    private String name;

    private String firstName;

    private String login;

    private String password;
}
