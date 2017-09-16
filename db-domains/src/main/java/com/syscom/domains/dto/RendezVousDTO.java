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
 * Classe DTO des rendez-vous médicaux.
 *
 * Created by Eric Legba on 11/08/17.
 */
@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
public class RendezVousDTO implements Serializable {

    private Long id;

    private LocalDateTime dateBegin;

    private LocalDateTime dateEnd;

    private Long patientId;
}
