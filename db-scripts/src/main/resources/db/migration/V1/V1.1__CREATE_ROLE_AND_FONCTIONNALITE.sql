-- Création de la table des rôles
CREATE TABLE T_ROLE(
  R_ID BIGINT NOT NULL,
  R_CODE VARCHAR(50) NOT NULL,
  R_LIBELLE VARCHAR(255) NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  PRIMARY KEY(R_ID),
  CONSTRAINT U_ROLE_CODE UNIQUE(R_CODE)
);

-- Commentaires sur la table des rôles et ses colonnes
COMMENT ON TABLE T_ROLE IS 'Table des rôles.';
COMMENT ON COLUMN T_ROLE.R_ID IS 'ID du rôle.';
COMMENT ON COLUMN T_ROLE.R_CODE IS 'Code du rôle';
COMMENT ON COLUMN T_ROLE.R_LIBELLE IS 'Libellé du rôle';
COMMENT ON COLUMN T_ROLE.CREATE_DATE IS 'Date de création';
COMMENT ON COLUMN T_ROLE.UPDATE_DATE IS 'Date de dernière mise à jour';

-- Création d'une sequence pour gérer les identifiants techniques des rôles
CREATE SEQUENCE ROLE_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Creation des rôles fondamentaux
INSERT INTO T_ROLE(R_ID, R_CODE, R_LIBELLE) VALUES (nextval('ROLE_SEQ'),'ADMIN','Administrateur');
INSERT INTO T_ROLE(R_ID, R_CODE, R_LIBELLE) VALUES (nextval('ROLE_SEQ'),'ASSISTANTE_DIRECTION','Assistante de direction');



-- Création de la table des fonctionnalités
CREATE TABLE T_FONCTIONNALITE(
  F_ID BIGINT NOT NULL,
  F_CODE VARCHAR(50) NOT NULL,
  F_LIBELLE VARCHAR(255) NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  CONSTRAINT U_FONCTIONNALITE_CODE UNIQUE(F_CODE), -- Contrainte d'unicité sur le code de la fonctionnalité
  PRIMARY KEY(F_ID) -- Définition de la clé primaire de la table
);

-- Commentaires sur la table des fonctionnalités et ses colonnes
COMMENT ON TABLE T_FONCTIONNALITE IS 'Table des fonctionnalités.';
COMMENT ON COLUMN T_FONCTIONNALITE.F_ID IS 'ID de la fonctionnalité.';
COMMENT ON COLUMN T_FONCTIONNALITE.F_CODE IS 'Code de la fonctionnalité.';
COMMENT ON COLUMN T_FONCTIONNALITE.F_LIBELLE IS 'Libellé de la fonctionnalité.';
COMMENT ON COLUMN T_FONCTIONNALITE.CREATE_DATE IS 'Date de création de la fonctionnalité.';
COMMENT ON COLUMN T_FONCTIONNALITE.UPDATE_DATE IS 'Date de dernière mise à jour.';

-- Création d'une sequence pour gérer les identifiants techniques des fonctionnalités
CREATE SEQUENCE FONCTIONNALITE_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Création de la table de jointure  T_ROLE_FONCTIONNALITE
CREATE TABLE T_ROLE_FONCTIONNALITE(
  R_ID BIGINT NOT NULL,
  F_ID BIGINT NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  CONSTRAINT FK_R_ID FOREIGN KEY(R_ID) REFERENCES T_ROLE(R_ID),
  CONSTRAINT FK_F_ID FOREIGN KEY(F_ID) REFERENCES T_FONCTIONNALITE(F_ID),
  PRIMARY KEY(R_ID,F_ID)
);

-- Commentaires sur la table de jointure et ses colonnes
COMMENT ON TABLE T_ROLE_FONCTIONNALITE IS 'Table de jointure entre les rôles et les fonctionnalités.';
COMMENT ON COLUMN T_ROLE_FONCTIONNALITE.R_ID IS 'ID du rôle.';
COMMENT ON COLUMN T_ROLE_FONCTIONNALITE.F_ID IS 'ID de la fonctionnalité.';