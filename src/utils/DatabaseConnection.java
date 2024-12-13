package utils;
import java.sql.*;
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:company.db"; // SQLite veritabanı dosyası
    private static Connection connection;

    // Private Constructor
    private DatabaseConnection() {}

    // Create the connection
    public static synchronized Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
        return connection;
    }

    // Close the connection
    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                //connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}