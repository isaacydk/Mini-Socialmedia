package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import Backend.Database;
import Backend.User;

public class CreateUser {

    private User u;
    private Database db;

    public CreateUser(User u, Database db){
        this.u = u;
        this.db= db;
    }

    public void create(){

        String sql = "INSERT INTO users(firstName, lastName, username, email, password) VALUES(?,?,?,?,?)";
        try (Connection conn = db.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, u.getFirstName());
        pstmt.setString(2, u.getLastName());
        pstmt.setString(3, u.getUsername());
        pstmt.setString(4, u.getUsername());
        pstmt.setString(5, u.getPassword());
        pstmt.executeUpdate();
        System.out.println("User inserted!");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    public User getUser(){
        u.setComments(new ArrayList<>());
        u.setFriends(new ArrayList<>());
        u.setLikes(new ArrayList<>());
        u.setPosts(new ArrayList<>());

        return u;
    }
}