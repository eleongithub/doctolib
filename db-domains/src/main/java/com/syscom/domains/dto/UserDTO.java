package com.syscom.domains.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class UserDTO implements Serializable {


    private String name;

    private String firstName;

    private String login;

    private String password;

    private String mail;

    private String role;
}
