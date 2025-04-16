-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 16 avr. 2025 à 15:23
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
-- Structure de la table `creneau`
--

DROP TABLE IF EXISTS `creneau`;
CREATE TABLE IF NOT EXISTS `creneau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_specialiste` int DEFAULT NULL,
  `date_heure` datetime NOT NULL,
  `disponible` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `id_specialiste` (`id_specialiste`)
) ENGINE=MyISAM AUTO_INCREMENT=188 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `creneau`
--

INSERT INTO `creneau` (`id`, `id_specialiste`, `date_heure`, `disponible`) VALUES
(1, 1, '2025-04-15 10:00:00', 0),
(2, 1, '2025-04-15 11:00:00', 1),
(3, 1, '2025-04-16 09:30:00', 0),
(4, 5, '2025-04-14 08:00:00', 0),
(5, 5, '2025-04-14 10:00:00', 1),
(6, 5, '2025-04-14 14:00:00', 1),
(7, 5, '2025-04-14 16:00:00', 1),
(8, 5, '2025-04-14 18:00:00', 0),
(9, 5, '2025-04-15 08:00:00', 0),
(10, 5, '2025-04-15 10:00:00', 0),
(11, 5, '2025-04-15 14:00:00', 0),
(12, 5, '2025-04-15 16:00:00', 1),
(13, 5, '2025-04-15 18:00:00', 0),
(14, 5, '2025-04-16 08:00:00', 1),
(15, 5, '2025-04-16 10:00:00', 1),
(16, 5, '2025-04-16 14:00:00', 1),
(17, 5, '2025-04-16 16:00:00', 1),
(18, 5, '2025-04-16 18:00:00', 1),
(19, 5, '2025-04-17 08:00:00', 1),
(20, 5, '2025-04-17 10:00:00', 1),
(21, 5, '2025-04-17 14:00:00', 1),
(22, 5, '2025-04-17 16:00:00', 1),
(23, 5, '2025-04-17 18:00:00', 1),
(24, 5, '2025-04-18 08:00:00', 0),
(25, 5, '2025-04-18 10:00:00', 1),
(26, 5, '2025-04-18 14:00:00', 1),
(27, 5, '2025-04-18 16:00:00', 1),
(28, 5, '2025-04-18 18:00:00', 1),
(29, 1, '2025-04-14 08:00:00', 1),
(30, 1, '2025-04-14 10:00:00', 0),
(31, 1, '2025-04-14 14:00:00', 1),
(32, 1, '2025-04-14 16:00:00', 1),
(33, 1, '2025-04-14 18:00:00', 1),
(34, 1, '2025-04-15 08:00:00', 0),
(35, 1, '2025-04-15 14:00:00', 0),
(36, 1, '2025-04-15 16:00:00', 1),
(37, 1, '2025-04-15 18:00:00', 1),
(38, 1, '2025-04-16 08:00:00', 0),
(39, 1, '2025-04-16 10:00:00', 1),
(40, 1, '2025-04-16 14:00:00', 1),
(41, 1, '2025-04-16 16:00:00', 0),
(42, 1, '2025-04-16 18:00:00', 1),
(43, 1, '2025-04-17 08:00:00', 1),
(44, 1, '2025-04-17 10:00:00', 1),
(45, 1, '2025-04-17 14:00:00', 1),
(46, 1, '2025-04-17 16:00:00', 1),
(47, 1, '2025-04-17 18:00:00', 1),
(48, 1, '2025-04-18 08:00:00', 1),
(49, 1, '2025-04-18 10:00:00', 1),
(50, 1, '2025-04-18 14:00:00', 0),
(51, 1, '2025-04-18 16:00:00', 1),
(52, 1, '2025-04-18 18:00:00', 1),
(53, 7, '2025-04-14 08:00:00', 1),
(54, 7, '2025-04-14 10:00:00', 0),
(55, 7, '2025-04-14 14:00:00', 1),
(56, 7, '2025-04-14 16:00:00', 1),
(57, 7, '2025-04-14 18:00:00', 1),
(58, 7, '2025-04-15 08:00:00', 0),
(59, 7, '2025-04-15 10:00:00', 1),
(60, 7, '2025-04-15 14:00:00', 1),
(61, 7, '2025-04-15 16:00:00', 1),
(62, 7, '2025-04-15 18:00:00', 1),
(63, 7, '2025-04-16 08:00:00', 1),
(64, 7, '2025-04-16 10:00:00', 1),
(65, 7, '2025-04-16 14:00:00', 1),
(66, 7, '2025-04-16 16:00:00', 1),
(67, 7, '2025-04-16 18:00:00', 1),
(68, 7, '2025-04-17 08:00:00', 1),
(69, 7, '2025-04-17 10:00:00', 1),
(70, 7, '2025-04-17 14:00:00', 1),
(71, 7, '2025-04-17 16:00:00', 1),
(72, 7, '2025-04-17 18:00:00', 1),
(73, 7, '2025-04-18 08:00:00', 0),
(74, 7, '2025-04-18 10:00:00', 1),
(75, 7, '2025-04-18 14:00:00', 1),
(76, 7, '2025-04-18 16:00:00', 1),
(77, 7, '2025-04-18 18:00:00', 1),
(78, 8, '2025-04-14 08:00:00', 1),
(79, 8, '2025-04-14 10:00:00', 1),
(80, 8, '2025-04-14 14:00:00', 1),
(81, 8, '2025-04-14 16:00:00', 1),
(82, 8, '2025-04-14 18:00:00', 1),
(83, 8, '2025-04-15 08:00:00', 0),
(84, 8, '2025-04-15 10:00:00', 0),
(85, 8, '2025-04-15 14:00:00', 1),
(86, 8, '2025-04-15 16:00:00', 1),
(87, 8, '2025-04-15 18:00:00', 1),
(88, 8, '2025-04-16 08:00:00', 1),
(89, 8, '2025-04-16 10:00:00', 1),
(90, 8, '2025-04-16 14:00:00', 1),
(91, 8, '2025-04-16 16:00:00', 1),
(92, 8, '2025-04-16 18:00:00', 1),
(93, 8, '2025-04-17 08:00:00', 1),
(94, 8, '2025-04-17 10:00:00', 1),
(95, 8, '2025-04-17 14:00:00', 1),
(96, 8, '2025-04-17 16:00:00', 1),
(97, 8, '2025-04-17 18:00:00', 1),
(98, 8, '2025-04-18 08:00:00', 1),
(99, 8, '2025-04-18 10:00:00', 1),
(100, 8, '2025-04-18 14:00:00', 1),
(101, 8, '2025-04-18 16:00:00', 1),
(102, 8, '2025-04-18 18:00:00', 1),
(103, 5, '2025-04-19 08:00:00', 1),
(104, 5, '2025-04-19 10:00:00', 1),
(105, 5, '2025-04-19 14:00:00', 1),
(106, 5, '2025-04-19 16:00:00', 1),
(107, 5, '2025-04-19 18:00:00', 1),
(108, 7, '2025-04-19 08:00:00', 1),
(109, 7, '2025-04-19 10:00:00', 1),
(110, 7, '2025-04-19 14:00:00', 1),
(111, 7, '2025-04-19 16:00:00', 1),
(112, 7, '2025-04-19 18:00:00', 1),
(113, 1, '2025-04-19 08:00:00', 1),
(114, 1, '2025-04-19 10:00:00', 1),
(115, 1, '2025-04-19 14:00:00', 1),
(116, 1, '2025-04-19 16:00:00', 1),
(117, 1, '2025-04-19 18:00:00', 1),
(118, 8, '2025-04-19 08:00:00', 1),
(119, 8, '2025-04-19 10:00:00', 1),
(120, 8, '2025-04-19 14:00:00', 1),
(121, 8, '2025-04-19 16:00:00', 1),
(122, 8, '2025-04-19 18:00:00', 1),
(123, 8, '2025-04-20 08:00:00', 1),
(124, 8, '2025-04-20 10:00:00', 1),
(125, 8, '2025-04-20 14:00:00', 1),
(126, 8, '2025-04-20 16:00:00', 1),
(127, 8, '2025-04-20 18:00:00', 1),
(128, 11, '2025-04-16 08:00:00', 0),
(129, 11, '2025-04-16 10:00:00', 1),
(130, 11, '2025-04-16 14:00:00', 1),
(131, 11, '2025-04-16 16:00:00', 1),
(132, 11, '2025-04-16 18:00:00', 1),
(133, 11, '2025-04-17 08:00:00', 1),
(134, 11, '2025-04-17 10:00:00', 1),
(135, 11, '2025-04-17 14:00:00', 1),
(136, 11, '2025-04-17 16:00:00', 1),
(137, 11, '2025-04-17 18:00:00', 1),
(138, 11, '2025-04-18 08:00:00', 1),
(139, 11, '2025-04-18 10:00:00', 1),
(140, 11, '2025-04-18 14:00:00', 1),
(141, 11, '2025-04-18 16:00:00', 1),
(142, 11, '2025-04-18 18:00:00', 1),
(143, 11, '2025-04-19 08:00:00', 1),
(144, 11, '2025-04-19 10:00:00', 1),
(145, 11, '2025-04-19 14:00:00', 1),
(146, 11, '2025-04-19 16:00:00', 1),
(147, 11, '2025-04-19 18:00:00', 1),
(148, 11, '2025-04-20 08:00:00', 1),
(149, 11, '2025-04-20 10:00:00', 1),
(150, 11, '2025-04-20 14:00:00', 1),
(151, 11, '2025-04-20 16:00:00', 1),
(152, 11, '2025-04-20 18:00:00', 1),
(153, 12, '2025-04-16 08:00:00', 1),
(154, 12, '2025-04-16 10:00:00', 1),
(155, 12, '2025-04-16 14:00:00', 1),
(156, 12, '2025-04-16 16:00:00', 1),
(157, 12, '2025-04-16 18:00:00', 1),
(158, 12, '2025-04-17 08:00:00', 1),
(159, 12, '2025-04-17 10:00:00', 1),
(160, 12, '2025-04-17 14:00:00', 1),
(161, 12, '2025-04-17 16:00:00', 1),
(162, 12, '2025-04-17 18:00:00', 1),
(163, 12, '2025-04-18 08:00:00', 1),
(164, 12, '2025-04-18 10:00:00', 1),
(165, 12, '2025-04-18 14:00:00', 1),
(166, 12, '2025-04-18 16:00:00', 1),
(167, 12, '2025-04-18 18:00:00', 1),
(168, 12, '2025-04-19 08:00:00', 1),
(169, 12, '2025-04-19 10:00:00', 1),
(170, 12, '2025-04-19 14:00:00', 1),
(171, 12, '2025-04-19 16:00:00', 1),
(172, 12, '2025-04-19 18:00:00', 1),
(173, 12, '2025-04-20 08:00:00', 1),
(174, 12, '2025-04-20 10:00:00', 1),
(175, 12, '2025-04-20 14:00:00', 0),
(176, 12, '2025-04-20 16:00:00', 1),
(177, 12, '2025-04-20 18:00:00', 1),
(178, 1, '2025-04-20 08:00:00', 1),
(179, 1, '2025-04-20 10:00:00', 1),
(180, 1, '2025-04-20 14:00:00', 1),
(181, 1, '2025-04-20 16:00:00', 1),
(182, 1, '2025-04-20 18:00:00', 1),
(183, 5, '2025-04-20 08:00:00', 1),
(184, 5, '2025-04-20 10:00:00', 1),
(185, 5, '2025-04-20 14:00:00', 1),
(186, 5, '2025-04-20 16:00:00', 1),
(187, 5, '2025-04-20 18:00:00', 1);

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
-- Structure de la table `rendez_vous`
--

DROP TABLE IF EXISTS `rendez_vous`;
CREATE TABLE IF NOT EXISTS `rendez_vous` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_patient` int DEFAULT NULL,
  `id_creneau` int DEFAULT NULL,
  `date_reservation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `note` text,
  PRIMARY KEY (`id`),
  KEY `id_patient` (`id_patient`),
  KEY `id_creneau` (`id_creneau`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `rendez_vous`
--

INSERT INTO `rendez_vous` (`id`, `id_patient`, `id_creneau`, `date_reservation`, `note`) VALUES
(1, 2, 1, '2025-04-14 10:46:54', NULL),
(2, 1, 1, '2025-04-14 11:09:31', NULL),
(3, 1, 3, '2025-04-14 11:09:43', NULL),
(4, 1, 35, '2025-04-14 11:33:18', NULL),
(5, 1, 11, '2025-04-14 11:33:58', NULL),
(6, 2, 30, '2025-04-14 11:40:14', NULL),
(7, 2, 50, '2025-04-14 11:40:43', NULL),
(8, 1, 38, '2025-04-14 11:42:01', NULL),
(9, 1, 8, '2025-04-14 11:52:10', NULL),
(10, 1, 41, '2025-04-14 11:52:33', NULL),
(11, 2, 13, '2025-04-14 11:53:54', NULL),
(12, 2, 4, '2025-04-14 11:59:48', NULL),
(13, 7, 24, '2025-04-14 12:02:37', NULL),
(14, 2, 73, '2025-04-14 12:03:33', NULL),
(15, 2, 54, '2025-04-14 13:32:23', NULL),
(16, 2, 9, '2025-04-14 14:09:54', NULL),
(17, 2, 83, '2025-04-14 14:47:33', NULL),
(18, 9, 34, '2025-04-14 15:58:32', NULL),
(19, 2, 58, '2025-04-15 07:14:02', NULL),
(20, 2, 10, '2025-04-16 13:35:02', NULL),
(21, 2, 84, '2025-04-16 13:35:10', NULL),
(22, 2, 175, '2025-04-16 14:46:47', NULL),
(23, 2, 128, '2025-04-16 15:19:59', NULL);

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
  `rue` varchar(100) DEFAULT NULL,
  `ville` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `role`, `specialisation`, `rue`, `ville`) VALUES
(1, 'Leclerc', 'Stanislas', 'Stan@gmail.com', 'azerty', 'specialiste', 'Maths', NULL, NULL),
(2, 'Test', 'TEst', 'test', 'Test', 'patient', NULL, NULL, NULL),
(3, 'A', 'B', 'A', 'B', 'admin', NULL, NULL, NULL),
(4, '', '', '', '', 'patient', NULL, NULL, NULL),
(5, 'Dupont', 'Marie', 'marie.dupont@med.fr', 'pwd', 'specialiste', 'Cardiologue', NULL, NULL),
(6, 'Martin', 'Lucie', 'lucie.martin@exemple.fr', 'pwd', 'patient', NULL, NULL, NULL),
(7, 'Désiré', 'Doué', 'DD@gmail.com', 'azerty', 'specialiste', 'LDC', NULL, NULL),
(8, 'Michelle', 'LeBlé', 'ML@gmail.com', 'ML', 'specialiste', 'Cardiologue', NULL, NULL),
(9, 's', 's', 's', 's', 'patient', NULL, NULL, NULL),
(10, 'Stanislas', 'Leclerc', 'S@gmail.com', 'azerty', 'admin', NULL, NULL, NULL),
(11, 'dembélé', 'ousmane ', 'OD@gmail.com', 'OD', 'specialiste', 'chirurgien', 'Michelle', 'Rennes'),
(12, 'Neves', 'Joao', 'NJ@gmail.com', 'NJ', 'specialiste', 'Dermatologue', 'Rue de Rennes', 'Paris');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
