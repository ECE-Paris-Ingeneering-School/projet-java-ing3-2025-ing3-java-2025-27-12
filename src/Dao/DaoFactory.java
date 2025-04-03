package Dao;

import java.sql.*;

/**
 * La DAO Factory permet d'initialiser la connexion à la base de données et fournit des DAO pour interagir avec les données.
 */
public class DaoFactory {
    private static String url;
    private String username;
    private String password;

    public DaoFactory() {
        this.url = "jdbc:mysql://localhost:3306/rendez_vous_specialistes";
        this.username = "root"; // ou ton utilisateur
        this.password = "";     // ou ton mot de passe
    }

    // Constructeur de DaoFactory
    public DaoFactory(String url, String username, String password) {
        DaoFactory.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode pour obtenir une instance de DaoFactory.
     * @param database La base de données.
     * @param username Le nom d'utilisateur pour la connexion.
     * @param password Le mot de passe pour la connexion.
     * @return Une instance de DaoFactory.
     */
    public static DaoFactory getInstance(String database, String username, String password) {
        try {
            // Chargement du driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de connexion à la base de données");
        }

        url = "jdbc:mysql://localhost:3306/" + database;
        return new DaoFactory(url, username, password);
    }

    /**
     * Méthode pour obtenir la connexion à la base de données.
     * @return La connexion à la base de données.
     * @throws SQLException Si une erreur se produit lors de la connexion.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Récupération des DAO pour les entités
    public PatientDAO getPatientDAO() {
        return new PatientDAOImpl(this); // ✔️
    }


    public SpecialisteDAO getSpecialisteDAO() {
        return new SpecialisteDAOImpl(this);
    }

    public LieuDAO getLieuDAO() {
        return new LieuDAOImpl(this);
    }

    public RendezVousDAO getRendezVousDAO() {
        return new RendezVousDAOImpl(this);
    }

    public UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOImpl(this);
    }

    /**
     * Méthode pour fermer la connexion à la base de données.
     */
    public void disconnect() {
        try (Connection conn = this.getConnection()) {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de déconnexion à la base de données");
        }
    }
}
