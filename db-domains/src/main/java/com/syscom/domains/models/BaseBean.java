package com.syscom.domains.models;

import com.syscom.domains.converters.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 *
 * Super-classe contenant les champs techniques associés  aux tables de la BDD.
 *
 * @author Eric Legba
 * @since 08/06/17 17:42
 */
@MappedSuperclass
@Data
@ToString(exclude = {})
@EqualsAndHashCode(exclude = {})
public class BaseBean implements Serializable {

	@Column(name = "CREATE_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	protected LocalDateTime createDate;

	@Column(name = "UPDATE_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	protected LocalDateTime updateDate;

	/**
	 * Avant d'enregistrer un objet en BDD, mise à jour des dates techniques s'ils étaient nulles
	 *
	 */
	@PrePersist
	void  prePersist(){
		this.createDate = this.createDate == null ? now()  : this.createDate;
		this.updateDate = this.updateDate == null ? now() : this.updateDate;
	}

	/**
	 * Mise à jour de la date de modification avant une opération de modification
	 *
	 */
	@PreUpdate
	void preUpdate(){
		this.updateDate = now();
	}

}
