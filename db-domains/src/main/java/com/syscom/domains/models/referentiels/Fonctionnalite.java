package com.syscom.domains.models.referentiels;

import com.syscom.domains.models.BaseBean;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"id", "roles"})
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
