-- Création de la tables des rendez-vous
CREATE TABLE T_RENDEZ_VOUS(
  R_ID BIGINT NOT NULL,
  R_DATE_BEGIN TIMESTAMP NOT NULL,
  R_DATE_END TIMESTAMP NOT NULL,
  R_PATIENT_ID BIGINT NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  PRIMARY KEY(R_ID),
  CONSTRAINT FK_PATIENT_ID FOREIGN KEY(R_PATIENT_ID) REFERENCES T_PATIENT(P_ID) -- Clé étrangère sur l'ID de la table T_PATIENT
);

-- Commentaires sur la table des rendez-vous et des colonnes
COMMENT ON TABLE T_RENDEZ_VOUS IS 'Table des rendez-vous';
COMMENT ON COLUMN T_RENDEZ_VOUS.R_ID IS 'ID du rendez-vous';
COMMENT ON COLUMN T_RENDEZ_VOUS.R_DATE_BEGIN IS 'Date de début du rendez-vous';
COMMENT ON COLUMN T_RENDEZ_VOUS.R_DATE_END IS 'Date de fin du rendez-vous';
COMMENT ON COLUMN T_RENDEZ_VOUS.R_PATIENT_ID IS 'ID du patient qui a pris le rendez-vous';
COMMENT ON COLUMN T_RENDEZ_VOUS.CREATE_DATE IS 'Date de création';
COMMENT ON COLUMN T_RENDEZ_VOUS.UPDATE_DATE IS 'Date de dernière mise à jour';


-- Création d'une sequence pour gérer les identifiants techniques des rendez-vous
CREATE SEQUENCE RENDEZ_VOUS_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;