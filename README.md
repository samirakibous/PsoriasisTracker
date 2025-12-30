# PsoriasisTracker

PsoriasisTracker est une application Android conçue pour aider les utilisateurs à suivre leurs symptômes, leurs traitements, leurs humeurs et leurs tâches liées à la gestion du psoriasis. L'application utilise Firebase pour la gestion des données et propose une interface intuitive pour une meilleure expérience utilisateur.

---+

## Fonctionnalités

### 1. **Suivi des symptômes**
- Les utilisateurs peuvent enregistrer et suivre différents symptômes (stress, humeur, surface affectée, démangeaisons, etc.) à l'aide de graphiques interactifs.
- Les données sont stockées dans Firebase Realtime Database.

### 2. **Gestion des traitements**
- Ajout de médicaments avec des informations telles que le nom, la description et la dose.
- Rappel des médicaments grâce à l'intégration d'`AlarmManager`.

### 3. **Gestion des tâches**
- Ajout et suivi des tâches liées à la gestion du psoriasis.
- Les tâches sont affichées dans une liste avec des options pour les supprimer ou les modifier.

### 4. **Communauté**
- Les utilisateurs peuvent interagir avec d'autres membres via une section communautaire.
- Publication et lecture de posts dans un flux communautaire.

### 5. **Suivi des notes**
- Ajout et modification de notes personnelles.
- Les notes sont synchronisées avec Firebase Firestore.

### 6. **Mode sombre**
- Option pour activer/désactiver le mode sombre dans les paramètres.

### 7. **Écran de démarrage**
- Vidéo d'introduction pour une expérience utilisateur engageante.

### 8. **Assistant virtuel Gemini**
- Un assistant virtuel intégré pour répondre aux questions des utilisateurs et fournir des conseils personnalisés.

---

## Structure du projet

### Répertoires principaux :
- **`app/src/main/java/com/example/samira`** : Contient les fichiers Java pour les activités, fragments, adaptateurs et modèles.
- **`app/src/main/res`** : Contient les ressources de l'application (layouts, animations, images, etc.).
- **`app/src/main/java/com/example/samira/model`** : Contient les classes de modèle pour les données (ex. `Post`, `MedicineLibrary`, `Task`).
- **`app/src/main/java/com/example/samira/adapter`** : Contient les adaptateurs pour les RecyclerViews (ex. `PostAdapter`, `NoteAdapter`).

---

## Technologies utilisées

- **Langage** : Java
- **Base de données** : Firebase Realtime Database, Firestore
- **Notifications** : AlarmManager
- **Graphiques** : MPAndroidChart
- **Authentification** : Firebase Authentication
- **UI/UX** : RecyclerView, Fragments, SeekBars, ProgressDialog

---

## Installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/PsoriasisTracker.git
   ```
2. Ouvrez le projet dans Android Studio.
3. Synchronisez les fichiers Gradle.
4. Compilez et exécutez le projet sur un émulateur ou un appareil physique.

---

## Contribution
Les contributions sont les bienvenues ! Si vous souhaitez contribuer, veuillez suivre ces étapes :
1. Forkez le dépôt.
2. Créez une nouvelle branche pour votre fonctionnalité ou correction de bug :
   ```bash
   git checkout -b feature/votre-nouvelle-fonctionnalite
   ```
3. Validez vos modifications et poussez-les vers votre fork :
   ```bash
   git commit -m "Ajoutez votre message de commit ici"
   git push origin feature/votre-nouvelle-fonctionnalite
   ```
4. Ouvrez une demande de tirage vers le dépôt principal.

---

## License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

---

## Auteurs
- [Samira Kibous](https://github.com/samirakibous)

---
