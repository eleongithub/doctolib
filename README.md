Doctolib
==

Doctolib est une application qui permet aux medecins de gérer leeurs rendez-vous avec les patients.
En s'inspirant de cette application, nous allons écrire une API qui permet de gérer des apptients
et leurs rendez-vous. C'est un cas pratique dont nous allons nous servir comme cas pratique
pour utiliser Spring Boot pour contruire une API qui peut être utiliser pour ce contexte.

Spring Boot
-

Spring Boot est un framework de Spring qui permet de simplifier le démarrage des applications Web
avec une meilleure gestion des configurations (plus besoin des nombreux fichiers XML de configuration, etc...).
Il embarque aussi un serveur Tomcat et gère mieux les dépendances avec l'import des modules "starter".
Dans ce cas pratique, nous utilisons Spring Boot pour produire une API Backend Spring Rest.

Architecture de l'application
-

L'application est décomposée en plusieurs couches que sont :
* le façade Web : elle expose les APIs disponibles pour les clients.
* la couche métier : elle comporte les beans Service qui implémente les logiques de traitements métier et la gestion des transactions.
* la couche DAO(Spring Data) : elle assure l'accès aux données avec le framework Spring Data.
* la couche ORM(JPA/Hibernate) : elle réalise le mapping Objet - Table de base de données.
* le versionning des scripts SQL.
* la base de données (PostgreSQL) : Les données sont stockées dans un SGBD PostgreSQL.

### Le façade Web #


Le façade Web est la partie accessible aux clients de l'API. Elle expose uniquement les APIs REST et n'effectue aucun traitement métier.
Elle utilise les beans service  de la couche inférieure et est codé dans le module db-restfull.Pour construire les API REST, nous utilisons le framework
[Spring REST](https://projects.spring.io/spring-restdocs/ "link to Spring REST").
Spring REST fournit les annotations pour contruire les façades REST :
* @RestController : elle est présente dans tous les contrôleurs qui exposent des APIs.
* @RequestMapping : Elle permet de faire le mapping entre une URL d'API et la méthode chargée de traiter ladite URL.
* @RequestParam : cette annotation faire un mapping entre un paramètre de l'API et le paramètre de la méthode qui traite l'API.
* etc....
Pour connaître les annotations et autres outils fournis par Spring REST, consulter le module db-restfull.
Par ailleurs, certains APIs sont sécurisés. Elles ne sont accessibles qu'aux clients authentifiés et disposant des droits nécessaires.
Pour gérer la sécurité des APIs, nous utilisons :
* une authentification par Token : Il s'agit d'une authentification de type Basic avec un login:Mot de passe en Base64.
Sil les credentials sont connus, un token de connection est fournis par l'API. Ce token sera rajouté aux headers des requêtes vers les APIs sécurisés.
* [Spring Security](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/ "link to Spring Security") : En utilisant l'annotation @Secured et d'autres mécanismes de sécurité, les APIs sécurisés
ne sont accessibles qu'aux clients disposant de tokens valides et des droits les autorisant à exploiter lesdidtes APIs.

### La couche métier #

La couche métier contient les contrats d'interface des services métiers et leurs implémentations. Elle est disponible dans le module db-service.
Les implémentations des services métiers sont des beans de service Spring caractérisés par l'annotation
@Service.
Aussi c'est dans cette couche que se gère les transactions(@Transactional), les exceptions fonctionnelles.
Pour consulter/modifier des données de la la base de données, elle s'appuie sur les repositories de la couche DAO.


### La couche DAO (Data Access Object) #

La couche DAO de l'application est diponible dans le module db-dao.
Elle est composée des interfaces Spring Data qui permet d'accéder aux données de l'applications.
Spring Data est un framework d'accès aux données fourni par l'écosystème Spring. Pourquoi utiliser ce framework ?
Il fournit des méthodes d'accès aux données sans qu'on écrive une ligne de données. Il facilite donc grandement la tâche du développeur.
Par exemple, Spring Data fournit d'éjà des méthodes telles que la recherche/suppression d'une ligne d'une table par ID, compter le nombre de ligne
dans une table, la persistence d'une ligne, etc... Avec ces méthodes fournies, Spring Data nous permet de gagner du temps et de diminuer
la taille des repositories d'accès aux données. En plus, Spring Data peut être combiner avec plusieurs SGBD relationnels ou de type NoSQL.

### La couche ORM(JPA/Hibernate) #

Cette couche (module db-domains) réalise le mapping entre les objets Java et les tables de la base de données.
JPA est Java Persistence API. Elle est une interface de la communauté Java qui définit des normes, des règles
à respecter pour réaliser le mapping Objet-Relationnel.
Hibernate est une implémentation de JPA qui respecte les règles, le standard défini par cette interface.
Dans toutes les classes réalisant les mappings Objet-Relationnel, nous utilisons les annotations
du package javax.persistence. Nous n'utiliserons aucune annotation Hibernate. Parmi les annotations les plus utilisées, nous avons :
* @Entity : au dessus d'une classe, elle indique que celle-ci est une classe persistante et qui a vocation à être enregistrer en base de données.
* @Table : elle contient un paramètre qui correspond au nom d'une table en base de données. Elle indique donc que cette classe est le reflet Java d'une table en base de données.
* @Column : elle est utilisée au dessus des attributs des classes et indique à quelle colonne de table l'attribut correspond. On y retrouve des paramètres
telles que le nom de la colonne, le type, la longueur, etc...
* etc...

### Le versionning des scripts SQL #

Le versionning des scripts SQL est assuré par le framework [flyway](https://flywaydb.org "link to flyway").
Tous les scripts SQL sont dans le module db-scripts, dans le sous-répertoire des ressources.
Ce module fait partir des dépendances du modules db-restfull. Au démarrage du serveur Tomcat embarqué, les scripts SQL sont exécutés
et la base de données est mise à jour automatiquement avec les nouveaux scripts SQL.
