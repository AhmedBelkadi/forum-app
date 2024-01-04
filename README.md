# Projet Forum JavaFX

## Présentation

Ce projet est une application de forum simple développée en JavaFX, permettant aux utilisateurs de poser des questions et de fournir des réponses. L'application suit l'architecture MVC et utilise JDBC pour l'intégration avec une base de données MySQL.

## Fonctionnalités

- **Authentification des Utilisateurs :** Les utilisateurs peuvent se connecter ou s'inscrire.
- **Publication de Questions et Réponses :** Possibilité de poser des questions et d'y répondre.
- **Consultation du Forum :** Affichage d'une liste de questions avec leurs réponses.
- **Fonctionnalités Utilisateur :** Les utilisateurs peuvent consulter leurs propres questions et les supprimer.

## Technologies Utilisées

- Java
- JavaFX
- JDBC
- MySQL 

## diagramme du classe 

![WhatsApp Image 2024-01-04 à 01 55 37_471354e9](https://github.com/AhmedBelkadi/forum-app/assets/136114058/21888f43-a5b2-4b01-a3bd-dc809b0edd59)


## Structure du Projet

- `src/main/java/` - Contient le code source Java
  - `com.example.forum` - Package principal
    - `controllers` - Classes de contrôleur
    - `dao` - Interfaces d'objet d'accès aux données (DAO)
    - `daoImpl` - Classes d'implémentation des DAO
    - `models` - Classes d'entité
  - `resources/` - Fichiers FXML pour l'interface utilisateur

- `src/main/resources/` - Contient les fichiers FXML pour la conception de l'interface utilisateur

## Utilisation

- Ouvrez l'application et naviguez à travers l'interface utilisateur.
- Connectez-vous ou inscrivez-vous pour accéder à des fonctionnalités supplémentaires.
