package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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



    // ✅ Fetch all posts from the database
    public List<Post> getAllPosts() {
    List<Post> posts = new ArrayList<>();
    String sql = "SELECT p.id, p.user_id, p.content, p.created_at, u.username " +
                 "FROM POSTS p " +
                 "JOIN USERS u ON p.user_id = u.id " +
                 "ORDER BY p.created_at DESC";

    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            String content = rs.getString("content");
            String username = rs.getString("username");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            Post post = new Post(userId, content, username, createdAt);
            post.setId(id); // if your Post has a setter
            posts.add(post);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return posts;
}


    public void addPost(Post post) {
    String sql = "INSERT INTO POSTS (user_id, content) VALUES (?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, post.getUserId());
        pstmt.setString(2, post.getContent());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    // Get like count for a specific post
public int getLikeCount(int postId) {
    String sql = "SELECT COUNT(*) FROM LIKES WHERE post_id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, postId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

// Add a like (userId liking a post)
public void addLike(int postId, int userId) {
    String sql = "INSERT OR IGNORE INTO LIKES (post_id, user_id) VALUES (?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, postId);
        pstmt.setInt(2, userId);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Remove a like
public void removeLike(int userId, int postId) {
    String sql = "DELETE FROM LIKES WHERE user_id = ? AND post_id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, userId);
        pstmt.setInt(2, postId);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
