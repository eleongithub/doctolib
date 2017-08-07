package com.syscom.domains.dto;

import com.syscom.domains.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

/**
 * Classe DTO des données des patients.
 *
 * Created by Eric Legba on 01/08/17.
 */

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
public class PatientDTO implements Serializable {

    private Long id;

    private String name;

    private String firstName;

    private String phone;

    private String mail;

    private String address;
}
