import Dao.DaoFactory;
import Dao.UtilisateurDAO;
import Modele.Utilisateur;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de DaoFactory
        DaoFactory daoFactory = DaoFactory.getInstance("rendez_vous_specialistes", "root", "");

        try {
            // Test de connexion à la base
            Connection connection = daoFactory.getConnection();
            System.out.println("✅ Connexion à la base de données réussie !");
            connection.close();

            // Utilisation du DAO pour récupérer les utilisateurs
            UtilisateurDAO utilisateurDAO = daoFactory.getUtilisateurDAO();
            List<Utilisateur> utilisateurs = utilisateurDAO.getAllUtilisateurs();

            System.out.println("📋 Liste des utilisateurs :");
            for (Utilisateur user : utilisateurs) {
                System.out.println("- ID: " + user.getIdUser()
                        + ", Email: " + user.getEmail()
                        + ", Mot de passe: " + user.getMotDePasse()
                        + ", Rôle: " + user.getRole());
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la connexion ou de l'accès à la base : " + e.getMessage());
        }
    }
}
