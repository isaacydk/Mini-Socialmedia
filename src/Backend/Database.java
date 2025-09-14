package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    public Database() {
        try {
            // Load the SQLite JDBC driver
            // Class.forName("sqlite-jdbc");

            // Connect to database (will create users.db in your project folder if not exists)
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");

            // Create users table if not exists
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "firstName TEXT NOT NULL," +
                         "lastName TEXT NOT NULL," +
                         "username TEXT UNIQUE NOT NULL," +
                         "email TEXT NOT NULL," +
                         "password TEXT NOT NULL)";
            conn.createStatement().execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // public Database(Database db){
    //     this.stmt = db.getStatement();
    // }
     public Connection getConnection() {
        return conn;
    }

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

    public boolean insertUser(String firstname, String lastname, String username, String email, String password) {
        String sql = "INSERT INTO users(firstname, lastname, username, email, password) VALUES(?,?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    // Validate login
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // true if found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
