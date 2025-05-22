# PortFlow Backend

Gestion optimisée des opérations portuaires.

## Description

Ce projet est une application Spring Boot conçue pour gérer et optimiser les flux de conteneurs, la planification des escales, la gestion des zones de stockage, et la surveillance des indicateurs de performance clés (KPI) dans un terminal portuaire.

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- PostgreSQL (ou une base de données compatible configurée dans `application.properties`)

## Installation et Lancement

1.  **Cloner le dépôt :**
    ```bash
    git clone [URL_DU_DEPOT]
    cd portflow-backend
    ```

2.  **Configurer la base de données :**
    - Assurez-vous que PostgreSQL est installé et en cours d'exécution.
    - Créez une base de données nommée `portflow`.
    - Mettez à jour les informations d'identification de la base de données dans `src/main/resources/application.properties` :
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/portflow
      spring.datasource.username=VOTRE_UTILISATEUR_BDD
      spring.datasource.password=VOTRE_MOT_DE_PASSE_BDD
      ```

3.  **Compiler le projet avec Maven :**
    ```bash
    mvn clean install
    ```

4.  **Lancer l'application :**
    ```bash
    java -jar target/portflow-backend.jar 
    ```
    (Remplacez `portflow-backend.jar` par le nom réel du JAR généré s'il est différent)

L'application sera accessible à l'adresse `http://localhost:8080`.

## Documentation de l'API

Une fois l'application lancée, la documentation Swagger UI sera disponible à l'adresse :
`http://localhost:8080/swagger-ui.html`

## Endpoints Principaux (Exemples)

- **Authentification :**
  - `POST /api/auth/login` : Connexion d'un utilisateur.
- **Gestion des Utilisateurs (Admin) :**
  - `GET /api/admin/users` : Lister tous les utilisateurs.
  - `POST /api/admin/users` : Créer un nouvel utilisateur.
- **Gestion des Conteneurs (Client) :**
  - `GET /api/client/containers` : Lister les conteneurs d'un client.
  - `GET /api/client/containers/{containerNumber}/track` : Suivre un conteneur spécifique.
- **Planification des Quais (Berth Planner) :**
  - `GET /api/berth-planner/ships/approaching` : Lister les navires en approche.
  - `POST /api/berth-planner/escales` : Créer une nouvelle escale.

*(Liste non exhaustive, se référer à la documentation Swagger pour plus de détails)*

## Structure du Projet

- `src/main/java` : Code source de l'application.
  - `com.ensias.portflow` : Package principal.
    - `controller` : Contrôleurs REST.
    - `service` : Logique métier.
    - `repository` : Accès aux données (JPA).
    - `model` : Entités JPA et DTOs.
    - `config` : Classes de configuration (Sécurité, BDD).
- `src/main/resources` : Fichiers de configuration et ressources.
- `src/test/java` : Tests unitaires et d'intégration.
- `pom.xml` : Fichier de configuration Maven.

## Contribution

Les contributions sont les bienvenues. Veuillez suivre les bonnes pratiques de développement et soumettre des Pull Requests. 