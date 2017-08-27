package com.syscom.domains.models.referentiels;

import com.syscom.domains.models.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Classe réalisant le mapping Objet - Relationnel des rôles des utilisateurs
 *
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"fonctionnalites"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"fonctionnalites"})
@Entity
@Table(name = "T_ROLE")
public class Role extends BaseBean {

    @Id
    @SequenceGenerator(name = "ROLE_SEQUENCE_GENERATOR", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQUENCE_GENERATOR")
    @Column(name = "R_ID")
    private Long id;

    @Column(name = "R_CODE", nullable = false)
    private String code;

    @Column(name = "R_LIBELLE", nullable = false)
    private String libelle;

    @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
    private List<Fonctionnalite> fonctionnalites;

}
