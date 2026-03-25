import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/chrionline?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Salah@2004";

    public static Connection ouvrirConnexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void fermerConnexion(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Connection connexion = null;
        try {
            connexion = ouvrirConnexion();
            System.out.println("Connexion réussie à la base chrionline !");
        } catch (SQLException e) {
            System.out.println("Erreur BD : " + e.getMessage());
            e.printStackTrace();
        } finally {
            fermerConnexion(connexion);
        }
    }
}