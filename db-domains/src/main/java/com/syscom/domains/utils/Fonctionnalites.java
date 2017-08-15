package com.syscom.domains.utils;

/**
 * Liste des fonctionnalités dédiées aux utilisateurs de l'application
 *
 * Created by Eric Legba on 29/07/17.
 */
public interface Fonctionnalites {

//    private Fonctionnalites() {
//        throw new IllegalAccessError("Fonctionnalites class");
//    }


    String ROLE_PREFIX = "ROLE_";
    String ROLE_AJOUTER_PATIENT = ROLE_PREFIX+"AJOUTER_PATIENT";
    String ROLE_CONSULTER_PATIENT = ROLE_PREFIX+"CONSULTER_PATIENT";
    String ROLE_MODIFIER_PATIENT = ROLE_PREFIX+"MODIFIER_PATIENT";
    String ROLE_SUPPRIMER_PATIENT = ROLE_PREFIX+"SUPPRIMER_PATIENT";
    String ROLE_CREER_RENDEZ_VOUS = ROLE_PREFIX+"AJOUTER_RENDEZ_VOUS";
    String ROLE_CONSULTER_RENDEZ_VOUS = ROLE_PREFIX+"CONSULTER_RENDEZ_VOUS";
    String ROLE_MODIFIER_RENDEZ_VOUS = ROLE_PREFIX+"MODIFIER_RENDEZ_VOUS";
    String ROLE_SUPPRIMER_RENDEZ_VOUS = ROLE_PREFIX+"SUPPRIMER_RENDEZ_VOUS";


}
