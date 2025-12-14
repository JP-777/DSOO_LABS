package DSOO_LABS.laboratorio9.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_bancario";
    private static final String USER = "banco_user";
    private static final String PASSWORD = "banco123";

    // Método que SIEMPRE devuelve una NUEVA conexión
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.err.println(" Error al conectar a MySQL: " + e.getMessage());
            return null;
        }
    }
    
    // NO necesitamos closeConnection() porque try-with-resources lo hace
}