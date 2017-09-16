package com.syscom.domains.models;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe Mapping Objet - Relationnel pour gérer les données des patients.
 *
 * Created by Eric Legba on 01/08/17.
 */
@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@Entity
@Table(name = "T_PATIENT")
public class Patient extends BaseBean {

    @Id
    @SequenceGenerator(name = "PATIENT_SEQ_GENERATOR", sequenceName = "PATIENT_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PATIENT_SEQ_GENERATOR")
    @Column(name = "P_ID")
    private Long id;

    @Column(name = "P_NAME", nullable = false)
    private String name;

    @Column(name = "P_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "P_PHONE", nullable = false)
    private String phone;

    @Column(name = "P_MAIL", nullable = false)
    private String mail;

    @Column(name = "P_ADDRESS")
    private String address;

}
