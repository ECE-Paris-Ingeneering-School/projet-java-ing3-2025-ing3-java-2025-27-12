# 🩺 Application de Gestion de Rendez-vous Médicaux

Ce projet est une application Java Swing avec JDBC permettant la **gestion de rendez-vous** entre des **patients** et des **spécialistes médicaux**. Il a été conçu dans le cadre d’un projet de programmation orientée objet (POO) en Java.

---

## 🚀 Fonctionnalités

### ✅ Connexion sécurisée
- Système de connexion par email et mot de passe
- Gestion de rôles : `patient` ou `admin`

### 👤 Espace Patient
- Prendre un rendez-vous (choix du spécialiste, date, heure)
- Consulter l’historique de ses rendez-vous
- Voir les lieux et horaires disponibles

### 🛠️ Espace Administrateur
- Gérer les utilisateurs (ajout/suppression)
- Ajouter de nouveaux spécialistes ou lieux
- Consulter les statistiques globales

### 📊 Statistiques (JFreeChart)
- Graphiques camembert et histogrammes à venir (en cours d’intégration)

---

## 🧱 Architecture du projet

Projet structuré en **MVC + DAO** :

