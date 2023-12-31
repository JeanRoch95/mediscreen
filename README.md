# Mediscreen 

## Projet OpenClassRoom Microservice Java SpringBoot
Ce projet utilise Java / SpringBoot & Docker

## Prérequis 
* Installer Docker

## Mise en route du projet 
1. Rendez vous a la racine du projet et exécutez `mvn clean install` sur chaque microservice en utilisant `cd <nom du microservice>`et `cd ..`pour vous déplacer.
2. Revenez a la racine du projet, lancer docker sur votre machine puis exécutez `docker-compose up --build` pour construire et démarrer vos conteneurs.
3. Le site est accessible a l'adresse : [http://localhost:9003/login](http://localhost:9003/login)

## Ajoutez vos données 
Des fichiers JSON pour les notes et SQL pour les patients sont disponibles dans les microservices correspondant, vous pouvez les utiliser pour insérer vos données.
Microservice patient : 
* jdbc:mysql://localhost:3306

Microservice note : 
* mongodb://localhost:27017

N'oubliez pas de changer votre username & password dans les fichiers application.properties.

## Connexion a l'application
* username : utilisateur
* password : mdp
