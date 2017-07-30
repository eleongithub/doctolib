package com.syscom.domains.models;


import com.syscom.domains.models.referentiels.Role;
import lombok.*;

import javax.persistence.*;

/**
 * Classe représentant les données d'un utilisateur de l'application
 *
 * @author el1638en
 * @since 08/06/17 17:42
 */
@Data
@Builder
@ToString(exclude = {"id", "password"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@Entity
@Table(name = "T_USER")
public class User extends BaseBean {

	@Id
	@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	@Column(name = "U_ID")
	private Long id;

	@Column(name = "U_NAME")
	private String name;

	@Column(name = "U_FIRST_NAME")
	private String firstName;

	@Column(name = "U_LOGIN")
	private String login;

	@Column(name = "U_PASSWORD")
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="U_ROLE_ID", nullable = false)
	private Role role;


}
