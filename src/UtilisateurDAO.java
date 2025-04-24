import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * DAO (Data Access Object) pour la gestion des utilisateurs.
 * Fournit des méthodes pour accéder, insérer et rechercher des utilisateurs dans la base de données.
 *  * @author Mathis
 *  * @version 1.0
 */
public class UtilisateurDAO {
    private Connection connection;
    /**
     * Constructeur initialisant le DAO avec une connexion JDBC.
     * @param connection Connexion à la base de données
     */
    public UtilisateurDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Récupère tous les spécialistes depuis la base.
     * @return liste des utilisateurs avec le rôle 'specialiste'
     */

    public List<Utilisateur> getSpecialistes() {
        List<Utilisateur> specialistes = new ArrayList<>();
                String sql = "SELECT id, nom, prenom, email, role, specialisation, ville, rue FROM utilisateur WHERE role = 'specialiste'";


                try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {



            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                            rs.getString("nom"),
                         rs.getString("prenom"),
                              rs.getString("email"),
                                rs.getString("role"),
                        rs  .getString("specialisation"),
                        rs  .getString("ville"),
                        rs  .getString("rue")
                );


                specialistes.add(u);
            }



         } catch (SQLException e) {
                    e.printStackTrace();

        }
        return specialistes;
    }
    /**
     * Recherche des utilisateurs pour un admin selon un critère (nom, email...).
     * @param critere texte de recherche
     * @return liste des utilisateurs correspondant au critère
     */
    public List<Utilisateur> chercherParCritereAdmin(String critere) throws SQLException {


        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur WHERE " +
                "nom LIKE ? OR prenom LIKE ? OR email LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(sql);

                String pattern = "%" + critere + "%";
                stmt.setString(1, pattern);
                stmt.setString(2, pattern);
                stmt.setString(3, pattern);
                ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            liste.add(new Utilisateur(
                    rs.getInt("id"),


                    rs.getString("nom"),
                    rs.getString("prenom"),


                    rs. getString("email"),
                    rs. getString("mot_de_passe"),
                    rs. getString("rue"),
                    rs. getString("ville"),
                    rs.     getString("specialisation"),
                    rs. getString("role")
            ));


        }
        return liste;
    }







    /**
     * Recherche les patients ayant eu des rendez-vous avec un spécialiste donné.
     * @param idSpecialiste ID du spécialiste connecté
     * @param critere critère de recherche
     * @return liste de patients
     */
    public List<Utilisateur> chercherPatientsPourSpecialiste(int idSpecialiste, String critere) {
        List<Utilisateur> liste = new ArrayList<>();



        String sql = """
        SELECT DISTINCT u.id, u.nom, u.prenom, u.email, u.mot_de_passe, u.rue, u.ville, u.specialisation, u.role
        FROM utilisateur u
            JOIN rendez_vous r ON u.id = r.id_patient
        JOIN creneau c ON r.id_creneau = c.id
        
        WHERE c.id_specialiste = ?
          AND u.role = 'patient'
          AND (u.nom LIKE ? OR u.prenom LIKE ? OR u.email LIKE ?)
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String critereLike = "%" + critere + "%";
            ps.setInt(1, idSpecialiste);


            ps. setString(2, critereLike);
            ps. setString(3, critereLike);
            ps. setString(4, critereLike);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");


                String mdp = rs.getString("mot_de_passe");
                String rue = rs.getString("rue");

                String ville = rs.getString("ville");
                String spec = rs.getString("specialisation");

                String role = rs.getString("role");

                liste.add(new Utilisateur(id, nom, prenom, email, mdp, rue, ville, spec, role));
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }

        return liste;
    }



    /**
     * Récupère toutes les spécialités distinctes des spécialistes.
     * @return liste des spécialités
     */
    public List<String> getSpecialitesDisponibles() throws SQLException {
        List<String> specialites = new ArrayList<>();
        String sql = "SELECT DISTINCT specialisation FROM utilisateur WHERE role = 'specialiste' AND specialisation IS NOT NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                specialites.add(rs.getString("specialisation"));
            }
        }

        return      specialites;
    }
    /**
     * Récupère toutes les villes disponibles où exercent les spécialistes.
     * @return liste des villes
     */
    public      List<String> getVillesDisponibles() throws SQLException {
            List<String> villes = new ArrayList<>();

        String sql = "SELECT DISTINCT ville FROM utilisateur WHERE role = 'specialiste' AND ville IS NOT NULL AND ville <> ''";
        PreparedStatement stmt = connection.prepareStatement(sql);


        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            villes.add(rs.getString("ville"));
        }
        return villes;
    }


}
