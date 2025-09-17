package Backend;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String username; // comes from USERS table
    private LocalDateTime createdAt;

    public Post(int userId, String content, String username, LocalDateTime createdAt) {
        this.userId = userId;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public String getContent() { return content; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
