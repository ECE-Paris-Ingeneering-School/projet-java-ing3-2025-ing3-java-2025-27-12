// ConnexionBDD.java
// ConnexionBDD.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/rendez_vous_specialiste";
    private static final String USER = "root"; // à adapter
    private static final String PASSWORD = "";  // à adapter

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

