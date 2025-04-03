import java.sql.*;

public class Inscription {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/rendez_vous_specialiste";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static boolean inscrirePatient(String nom, String prenom, String email, String motDePasse, boolean estParticulier) {
        String type_patient = estParticulier ? "Particulier" : "Professionnel";
        String queryPatient = "INSERT INTO Patient (nom, prenom, email, mot_de_passe, type_patient) VALUES (?, ?, ?, ?, ?)";
        String queryUtilisateur = "INSERT INTO Utilisateur (email, mot_de_passe, role) VALUES (?, ?, 'patient')";

        try (Connection conn = getConnection();
             PreparedStatement stmtPatient = conn.prepareStatement(queryPatient);
             PreparedStatement stmtUtilisateur = conn.prepareStatement(queryUtilisateur)) {

            stmtPatient.setString(1, nom);
            stmtPatient.setString(2, prenom);
            stmtPatient.setString(3, email);
            stmtPatient.setString(4, motDePasse);
            stmtPatient.setString(5, type_patient);

            stmtUtilisateur.setString(1, email);
            stmtUtilisateur.setString(2, motDePasse);

            stmtPatient.executeUpdate();
            stmtUtilisateur.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

