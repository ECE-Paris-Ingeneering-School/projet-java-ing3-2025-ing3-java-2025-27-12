-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 15 avr. 2025 à 07:18
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
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `role`, `specialisation`) VALUES
(1, 'Leclerc', 'Stanislas', 'Stan@gmail.com', 'azerty', 'specialiste', 'Maths'),
(2, 'Test', 'TEst', 'test', 'Test', 'patient', NULL),
(3, 'A', 'B', 'A', 'B', 'admin', NULL),
(4, '', '', '', '', 'patient', NULL),
(5, 'Dupont', 'Marie', 'marie.dupont@med.fr', 'pwd', 'specialiste', 'Cardiologue'),
(6, 'Martin', 'Lucie', 'lucie.martin@exemple.fr', 'pwd', 'patient', NULL),
(7, 'Désiré', 'Doué', 'DD@gmail.com', 'azerty', 'specialiste', 'LDC'),
(8, 'Michelle', 'LeBlé', 'ML@gmail.com', 'ML', 'specialiste', 'Cardiologue'),
(9, 's', 's', 's', 's', 'patient', NULL),
(10, 'Stanislas', 'Leclerc', 'S@gmail.com', 'azerty', 'admin', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
