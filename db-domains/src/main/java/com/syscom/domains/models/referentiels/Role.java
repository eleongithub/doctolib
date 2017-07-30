package com.syscom.domains.models.referentiels;

import com.syscom.domains.models.BaseBean;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Classe réalisant le mapping Objet <==> Relationnel des rôles des urilisateurs
 *
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"id", "fonctionnalites"})
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

    @ManyToMany(mappedBy="roles")
    private List<Fonctionnalite> fonctionnalites;

}
