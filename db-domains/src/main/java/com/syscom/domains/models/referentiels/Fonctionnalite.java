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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Classe définissant les informations des fonctionnalités de l'application.
 *
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"roles"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles"})
@Entity
@Table(name = "T_FONCTIONNALITE")
public class Fonctionnalite extends BaseBean {

    @Id
    @SequenceGenerator(name = "FONCTIONNALITE_SEQUENCE_GENERATOR", sequenceName = "FONCTIONNALITE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FONCTIONNALITE_SEQUENCE_GENERATOR")
    @Column(name = "F_ID")
    private Long id;

    @Column(name = "F_CODE", nullable = false)
    private String code;

    @Column(name = "F_LIBELLE", nullable = false)
    private String libelle;

    @ManyToMany
    @JoinTable(
            name="T_ROLE_FONCTIONNALITE",
            joinColumns=@JoinColumn(name="F_ID", referencedColumnName="F_ID"),
            inverseJoinColumns=@JoinColumn(name="R_ID", referencedColumnName="R_ID"))
    private List<Role> roles;
}
