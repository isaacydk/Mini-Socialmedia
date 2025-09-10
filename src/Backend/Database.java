package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Statement stmt;

    public Connection connect() {
        Connection conn = null;
        try {
            // SQLite DB file (if not exists, it will be created)
            String url = "jdbc:sqlite:users.db";
            conn = DriverManager.getConnection(url);
            System.out.println("✅ Connected to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Statement getStatement(){
        return stmt;
    }
}
