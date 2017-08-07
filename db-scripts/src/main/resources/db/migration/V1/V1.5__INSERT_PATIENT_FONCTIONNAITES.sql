-- Insertion des nouvelles fonctionnalités liées aux patients
INSERT INTO T_FONCTIONNALITE(F_ID, F_CODE, F_LIBELLE) VALUES (nextval('FONCTIONNALITE_SEQ'),'F_AJOUTER_PATIENT','Ajouter un nouveau patient');
INSERT INTO T_FONCTIONNALITE(F_ID, F_CODE, F_LIBELLE) VALUES (nextval('FONCTIONNALITE_SEQ'),'F_CONSULTER_PATIENT','Consulter un patient');
INSERT INTO T_FONCTIONNALITE(F_ID, F_CODE, F_LIBELLE) VALUES (nextval('FONCTIONNALITE_SEQ'),'F_MODIFIER_PATIENT','Modifier un patient');
INSERT INTO T_FONCTIONNALITE(F_ID, F_CODE, F_LIBELLE) VALUES (nextval('FONCTIONNALITE_SEQ'),'F_SUPPRIMER_PATIENT','Supprimer un patient');

-- Insertion des données dans la table jointure  T_ROLE_FONCTIONNALITE
INSERT INTO T_ROLE_FONCTIONNALITE(R_ID,F_ID)
SELECT R_ID, F_ID
FROM T_ROLE R1, T_FONCTIONNALITE F1
WHERE R1.R_CODE IN('ADMIN', 'ASSISTANTE_DIRECTION') AND   F1.F_CODE IN('F_AJOUTER_PATIENT', 'F_CONSULTER_PATIENT', 'F_MODIFIER_PATIENT', 'F_SUPPRIMER_PATIENT');