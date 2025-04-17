import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private Connection connection;

    public UtilisateurDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Utilisateur> getSpecialistes() {
        List<Utilisateur> specialistes = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur WHERE role = 'specialiste'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                specialistes.add(new Utilisateur(id, nom, prenom, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialistes;
    }
    public List<Utilisateur> chercherPatientsPourSpecialiste(int idSpecialiste, String critere) {
        List<Utilisateur> liste = new ArrayList<>();

        String sql = """
SELECT DISTINCT u.id, u.nom, u.prenom, u.email
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
            ps.setString(2, critereLike);
            ps.setString(3, critereLike);
            ps.setString(4, critereLike);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");

                liste.add(new Utilisateur(id, nom, prenom, email));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
    public List<String> getSpecialitesDisponibles() throws SQLException {
        List<String> specialites = new ArrayList<>();
        String sql = "SELECT DISTINCT specialisation FROM utilisateur WHERE role = 'specialiste' AND specialisation IS NOT NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                specialites.add(rs.getString("specialisation"));
            }
        }

        return specialites;
    }


}
