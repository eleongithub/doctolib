package com.syscom.domains.models;

import com.syscom.domains.converters.LocalDateTimeConverter;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Classe de mapping Objet - Relationnel des jetons d'authentification.
 *
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"user"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user"})
@Entity
@Table(name = "T_TOKEN")
public class Token extends BaseBean {

    @Id
    @SequenceGenerator(name = "TOKEN_SEQUENCE_GENERATOR", sequenceName = "TOKEN_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_SEQUENCE_GENERATOR")
    @Column(name = "T_ID")
    private Long id;

    @Column(name = "T_VALUE", nullable = false)
    private String value;

    @Column(name = "T_EXPIRATION_DATE", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateExpiration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_USER_ID")
    private User user;

}
