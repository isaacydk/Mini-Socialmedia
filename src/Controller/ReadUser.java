package Controller;
import Backend.Database;
import Backend.User;
import java.sql.*;

public class ReadUser {

    private boolean loggedIn;
    private User user;

    public ReadUser(String username, String password, Database db) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?"; 
        // ⚠️ For production: store password_hash and use BCrypt instead!

        try (PreparedStatement ps = db.connect().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // Replace this with hashed password check

            ResultSet rs = ps.executeQuery();
            loggedIn = rs.next();

            if (loggedIn) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                // Don’t set raw password in object!
                // user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}


