package com.syscom.domains.models;


import com.syscom.domains.models.referentiels.Role;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe représentant les données d'un utilisateur.
 *
 * @author Eric Legba
 * @since 08/06/17 17:42
 */
@Data
@Builder
@ToString(exclude = {"password"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"role"})
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="U_ROLE_ID", nullable = false)
	private Role role;


}
