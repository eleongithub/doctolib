-- Création de la tables des tokens d'authentification
CREATE TABLE T_TOKEN(
  T_ID BIGINT NOT NULL,
  T_VALUE VARCHAR(255) NOT NULL,
  T_EXPIRATION_DATE TIMESTAMP NOT NULL,
  T_USER_ID BIGINT NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  PRIMARY KEY(T_ID),
  CONSTRAINT U_TOKEN_VALUE UNIQUE(T_VALUE), --Contrainte d'unicité sur la colonne des valeurs des tokens
  CONSTRAINT FK_USER_ID FOREIGN KEY(T_USER_ID) REFERENCES T_USER(U_ID) -- Clé étrangère sur l'ID de la table T_USER
);

-- Commentaires sur la table des tokens et ses colonnes
-- Commentaires sur la table des fonctionnalités et ses colonnes
COMMENT ON TABLE T_TOKEN IS 'Table des jetons d''authentification';
COMMENT ON COLUMN T_TOKEN.T_ID IS 'ID du token';
COMMENT ON COLUMN T_TOKEN.T_VALUE IS 'Valeur (GUID) du token';
COMMENT ON COLUMN T_TOKEN.T_EXPIRATION_DATE IS 'Date d''expiration du token';
COMMENT ON COLUMN T_TOKEN.T_USER_ID IS 'ID de l''utilisateur auquel est adossé le token';
COMMENT ON COLUMN T_TOKEN.CREATE_DATE IS 'Date de création';
COMMENT ON COLUMN T_TOKEN.UPDATE_DATE IS 'Date de dernière mise à jour';


-- Création d'une sequence pour gérer les identifiants techniques des tokens
CREATE SEQUENCE TOKEN_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;