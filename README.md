🩺 PsoriasisTracker

PsoriasisTracker est une application Android dédiée à l’accompagnement des personnes atteintes de psoriasis.
Elle permet de suivre l’évolution des symptômes, de gérer les traitements, d’organiser les tâches quotidiennes et de bénéficier d’un soutien communautaire, le tout dans une interface simple, moderne et sécurisée.

L’application s’appuie sur Firebase pour la gestion des données et propose une expérience utilisateur fluide et intuitive.

✨ Fonctionnalités principales
📊 Suivi des symptômes

Enregistrement quotidien des symptômes :
stress, humeur, surface affectée, démangeaisons, etc.

Visualisation de l’évolution via des graphiques interactifs.

Données stockées et synchronisées avec Firebase Realtime Database.

💊 Gestion des traitements

Ajout et gestion des médicaments :

Nom

Description

Dosage

Rappels automatiques grâce à l’intégration d’AlarmManager.

📝 Gestion des tâches

Création de tâches liées à la gestion du psoriasis (soins, rendez-vous, habitudes).

Affichage sous forme de liste.

Possibilité de modifier ou supprimer les tâches.

👥 Communauté

Espace communautaire pour :

Publier des posts

Lire et interagir avec les publications des autres utilisateurs

Favorise le partage d’expériences et le soutien moral.

📒 Notes personnelles

Création et édition de notes personnelles.

Synchronisation en temps réel avec Firebase Firestore.

🌙 Mode sombre

Activation / désactivation du Dark Mode depuis les paramètres.

Meilleur confort visuel, notamment la nuit.

🎬 Écran de démarrage

Vidéo d’introduction pour une expérience utilisateur immersive dès le lancement de l’application.

🤖 Assistant virtuel Gemini

Assistant intelligent intégré pour :

Répondre aux questions des utilisateurs

Fournir des conseils personnalisés liés au psoriasis

🗂️ Structure du projet
📁 Répertoires principaux
app/
 └── src/main/
     ├── java/com/example/samira/
     │   ├── model/        → Classes de données (Post, Task, Medicine, etc.)
     │   ├── adapter/     → Adapters RecyclerView
     │   ├── activities/  → Activities
     │   └── fragments/   → Fragments
     └── res/
         ├── layout/      → Fichiers XML UI
         ├── drawable/    → Images & icônes
         ├── anim/        → Animations
         └── values/      → Thèmes, couleurs, styles

🛠️ Technologies utilisées

Langage : Java

Plateforme : Android

Base de données :

Firebase Realtime Database

Firebase Firestore

Authentification : Firebase Authentication

Notifications : AlarmManager

Graphiques : MPAndroidChart

UI/UX :

RecyclerView

Fragments

SeekBars

ProgressDialog

🚀 Installation

Clonez le dépôt :

git clone https://github.com/votre-utilisateur/PsoriasisTracker.git


Ouvrez le projet dans Android Studio.

Synchronisez les fichiers Gradle.

Configurez Firebase (google-services.json).

Lancez l’application sur un émulateur ou un appareil physique.

🤝 Contribution

Les contributions sont les bienvenues !

Forkez le dépôt

Créez une branche :

git checkout -b feature/ma-nouvelle-fonctionnalite


Commitez vos changements :

git commit -m "Ajout d'une nouvelle fonctionnalité"


Poussez vers votre fork :

git push origin feature/ma-nouvelle-fonctionnalite


Ouvrez une Pull Request 🚀

📄 Licence

Ce projet est sous licence MIT.
Voir le fichier LICENSE pour plus d’informations.
