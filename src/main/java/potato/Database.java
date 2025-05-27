package potato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String url = "jdbc:postgresql://localhost:5430/postgres_db";
            String user = "postgres_user";
            String password = "postgres_password";

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}