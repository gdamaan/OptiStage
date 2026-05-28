# 🚀 OptiStage

OptiStage est une plateforme centralisée conçue pour simplifier et optimiser la gestion des stages. Elle fait le pont entre les étudiants à la recherche d'une expérience professionnelle, les entreprises proposant des offres, et les administrateurs/tuteurs supervisant le processus.

## ✨ Fonctionnalités Principales

- **Authentification Sécurisée** : Système de connexion robuste basé sur des tokens JWT avec hachage des mots de passe via Argon2.
- **Espaces Dédiés** : Tableaux de bord personnalisés selon le rôle (Étudiant, Tuteur, Administrateur).
- **Gestion des Offres** : Création, consultation et gestion des offres de stage.
- **Suivi des Candidatures** : Interface permettant aux étudiants de postuler et aux recruteurs d'évaluer les profils.
- **Base de Données Relationnelle** : Architecture de données solide propulsée par Hibernate (ORM).

## 🛠️ Stack Technique

### Backend (API REST)
- **Langage** : Java 17
- **Framework Web** : JAX-RS (Jersey) avec serveur Grizzly
- **ORM & Base de données** : Hibernate / MySQL
- **Sécurité** : JSON Web Tokens (jjwt), Argon2 (hachage)
- **Build Tool** : Maven

### Frontend (Interface Client)
- **Bibliothèque** : React 18
- **Build Tool** : Vite
- **Stylisation** : Tailwind CSS & PostCSS
- **Routage** : React Router DOM
- **Requêtes HTTP** : Axios

---

## 🚀 Installation & Déploiement en Local

Pour faire tourner ce bijou de technologie sur votre machine, veuillez suivre les étapes ci-dessous.

### 1. Prérequis
Assurez-vous d'avoir installé sur votre système :
- [Java Development Kit (JDK) 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (assurez-vous qu'il est bien dans votre `PATH`)
- [Node.js](https://nodejs.org/) (version 18 ou supérieure)
- Un serveur MySQL local (ex: XAMPP, WAMP, ou Docker)

### 2. Configuration de la Base de Données
1. Lancez votre serveur MySQL.
2. Créez une base de données nommée `optistage_db` (ou le nom spécifié dans vos configurations).
3. Ouvrez le fichier `src/main/resources/hibernate.cfg.xml` du projet backend et vérifiez que les identifiants de connexion (`connection.username` et `connection.password`) correspondent à votre installation locale.

### 3. Lancement du Backend

Ouvrez un terminal à la racine du dossier backend (OptiStage) et exécutez les commandes suivantes :
Bash

#### 1. Nettoyer et compiler le projet en un fichier .jar exécutable
mvn clean package

#### 2. Lancer le serveur d'API REST
java -jar target/Optistage_backend-1.0-SNAPSHOT.jar

L'API sera alors accessible, par défaut, sur http://localhost:8080/api.
### 4. Lancement du Frontend

Ouvrez un second terminal à la racine du dossier frontend (optistage-front) et exécutez :
Bash

#### 1. Installer les dépendances Node.js
npm install

#### 2. Lancer le serveur de développement Vite
npm run dev

L'application s'ouvrira dans votre navigateur, généralement sur http://localhost:5173.

## 🏗️ Déploiement en Production
Pour le Backend

Le fichier .jar généré par Maven (mvn clean package) contient toutes les dépendances nécessaires (Fat JAR). Il vous suffit de le transférer sur votre serveur (VPS, AWS, etc.) avec un environnement d'exécution Java 17 et de l'exécuter. Vous pouvez utiliser un service comme systemd sur Linux pour le maintenir en vie en arrière-plan.
Pour le Frontend

Pour générer les fichiers optimisés pour la production, exécutez :
Bash

npm run build

Le dossier dist/ généré contiendra les fichiers statiques (HTML, CSS, JS) que vous pourrez héberger sur n'importe quel serveur web classique (Nginx, Apache, Vercel, Netlify...).

Projet développé par Amaan GHULAM DIN, dans le cadre du projet de fin d'année.