-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 03 avr. 2025 à 08:15
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `rendez_vous_specialiste`
--

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

DROP TABLE IF EXISTS `lieu`;
CREATE TABLE IF NOT EXISTS `lieu` (
  `id_lieu` int NOT NULL AUTO_INCREMENT,
  `nom_lieu` varchar(100) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  PRIMARY KEY (`id_lieu`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `lieu`
--

INSERT INTO `lieu` (`id_lieu`, `nom_lieu`, `adresse`) VALUES
(1, 'Hôpital Saint-Louis', '123 Rue de Paris, Paris'),
(2, 'Clinique Montparnasse', '456 Rue de Lyon, Lyon');

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `id_rdv` int NOT NULL AUTO_INCREMENT,
  `date_heure` datetime NOT NULL,
  `id_patient` int DEFAULT NULL,
  `id_specialiste` int DEFAULT NULL,
  `id_lieu` int DEFAULT NULL,
  `note_patient` text,
  PRIMARY KEY (`id_rdv`),
  KEY `id_patient` (`id_patient`),
  KEY `id_specialiste` (`id_specialiste`),
  KEY `id_lieu` (`id_lieu`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `rendezvous`
--

INSERT INTO `rendezvous` (`id_rdv`, `date_heure`, `id_patient`, `id_specialiste`, `id_lieu`, `note_patient`) VALUES
(1, '2025-04-10 09:00:00', 1, 1, 1, 'Très bon rendez-vous, le spécialiste était très clair.'),
(2, '2025-04-12 14:30:00', 2, 2, 2, 'Rendez-vous rapide et efficace.');

-- --------------------------------------------------------

--
-- Structure de la table `specialiste_lieu`
--

DROP TABLE IF EXISTS `specialiste_lieu`;
CREATE TABLE IF NOT EXISTS `specialiste_lieu` (
  `id_specialiste` int NOT NULL,
  `id_lieu` int NOT NULL,
  PRIMARY KEY (`id_specialiste`,`id_lieu`),
  KEY `id_lieu` (`id_lieu`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `specialiste_lieu`
--

INSERT INTO `specialiste_lieu` (`id_specialiste`, `id_lieu`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `role` enum('patient','admin','specialiste') DEFAULT NULL,
  `specialisation` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `role`, `specialisation`) VALUES
(1, 'Leclerc', 'Stanislas', 'Stan@gmail.com', 'azerty', 'specialiste', 'Maths'),
(2, 'Test', 'TEst', 'test', 'Test', 'patient', NULL),
(3, 'A', 'B', 'A', 'B', 'admin', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
