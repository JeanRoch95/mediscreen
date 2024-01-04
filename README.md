# Mediscreen 

## Projet OpenClassRoom Microservice Java SpringBoot
Ce projet utilise Java / SpringBoot & Docker

## Prérequis 
* Installer Docker

## Mise en route du projet 
1. Rendez vous a la racine du projet et exécutez `mvn clean install` sur chaque microservice en utilisant `cd <nom du microservice>`et `cd ..`pour vous déplacer.
2. Revenez a la racine du projet, lancer docker sur votre machine puis exécutez `docker-compose up --build` pour construire et démarrer vos conteneurs.
3. Le site est accessible a l'adresse : [http://localhost:9003/login](http://localhost:9003/login)

## Connexion a l'application
* username : utilisateur
* password : mdp

## Utilisation du green-code
* Optimiser les termes déclencheurs dans le service risk.
* Documenter le code pour améliorer la maintenabilité.
* Utilisation de sonar-lint qui aide à identifier et corriger les problèmes de qualité et de sécurité.

## Affichage du niveau de risque 
Les niveaux de risque sont par défaut vides, il vous suffit de rajouter une note avec ou sans termes déclencheurs pour que le calcul du risk s'effectue et s'affiche.
