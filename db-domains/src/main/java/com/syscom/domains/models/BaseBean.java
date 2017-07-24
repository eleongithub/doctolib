package com.syscom.domains.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

/**
 * @author el1638en
 * @since 08/06/17 17:42
 */
@MappedSuperclass
@Data
@ToString(exclude = {})
@EqualsAndHashCode(exclude = {})
public class BaseBean implements Serializable {

	@Column(name = "CREATE_DATE")
	protected Date createDate;

	@Column(name = "UPDATE_DATE")
	protected Date updateDate;

	/**
	 * Avant un persist en BDD, mise à jour des dates techniques s'ils étaient nulles
	 *
	 */
	@PrePersist
	void  prePersist(){
		this.createDate = (this.createDate == null ? new Date() : this.createDate);
		this.updateDate = (this.updateDate == null ? new Date() : this.updateDate);
	}

	/**
	 * Mise à jour de la date de modification avant une opération de modification
	 *
	 */
	@PreUpdate
	void preUpdate(){
		this.updateDate = new Date();
	}

}
