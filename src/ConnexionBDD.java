
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
/**
 * Classe utilitaire responsable de la connexion à la base de données.
 * Utilise une base de données MySQL.
 *
 * @author Arthur
 * @version 1.0
 */
public class ConnexionBDD {
    /**
     * Établit et retourne une connexion à la base de données.
     *
     * @return une instance de {@link Connection} vers la base de données
     * @throws SQLException en cas d'erreur de connexion à la base
     */
    private static final String URL = "jdbc:mysql://localhost:3306/rendez_vous_specialiste";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnexion() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}

