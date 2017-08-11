package com.syscom.domains.models;

import com.syscom.domains.converters.LocalDateTimeConverter;
import com.syscom.domains.models.referentiels.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Classe des rendez-vous médicaux.
 *
 * Created by Eric Legba on 11/08/17.
 */

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@Entity
@Table(name = "T_USER")
public class RendezVous extends BaseBean {


    @Id
    @SequenceGenerator(name = "RENDEZ_VOUS_SEQ_GENERATOR", sequenceName = "RENDEZ_VOUS_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RENDEZ_VOUS_SEQ_GENERATOR")
    @Column(name = "R_ID")
    private Long id;

    @Column(name = "R_DATE_BEGIN", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateBegin;

    @Column(name = "R_DATE_END", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="R_PATIENT_ID", nullable = false)
    private Patient patient;


}
