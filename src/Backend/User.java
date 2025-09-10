package Backend;
import java.sql.*;
import java.util.ArrayList;

public class User {
    
    private int id;
    private String firstName;
    private String lastName;
    private String username; 
    private String password;
    private String email;
    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;
    private ArrayList<Post> likes;
    private ArrayList<User> friends;

    // public User(String fistName, String lastName, String username,  String email, String password){
    
    //     this.firstName = fistName;
    //     this.lastName = lastName;
    //     this.username = username;
    //     this.email = email;
    //     this.password = password;
        
    // }

    // public static void showDatabase(){
    //     String sql = "SELECT * FROM users";

    //     try (Connection conn = connect();
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery(sql)) {

    //         while (rs.next()) {
    //             System.out.println(
    //                 rs.getInt("id") + "\t" +
    //                 rs.getString("firstName") + "\t" +
    //                 rs.getString("lastName") + "\t" +
    //                 rs.getString("username") + "\t" +
    //                 rs.getString("email") + "\t" +
    //                 rs.getString("password")
    //             );
    //         }

    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    // } ------------------------------------------------------------------------------------------------- 
    
    // public static void deleteUserById(int id) {
    //     String sql = "DELETE FROM users WHERE id = ?";

    //     try (Connection conn = connect();
    //          PreparedStatement pstmt = conn.prepareStatement(sql)) {

    //         pstmt.setInt(1, id);
    //         int affectedRows = pstmt.executeUpdate();

    //         if (affectedRows > 0) {
    //             System.out.println("User with ID " + id + " deleted successfully.");
    //         } else {
    //             System.out.println("No user found with ID " + id);
    //         }

    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    // }
    
    public static Connection connect() {
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
    
    public void saveData(){
        
        String sql = "INSERT INTO users(firstName, lastName, username, email, password) VALUES(?,?,?,?,?)";
        try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, username);
        pstmt.setString(4, email);
        pstmt.setString(5, password);
        pstmt.executeUpdate();
        System.out.println("User inserted!");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    //setter and getter methods
    public void setId(int id){
        this.id = id;
    }
    public int getId (){
        return id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName; 
    }
    public String getLastName(){
        return lastName; 
    }
    public String getName(){
        return firstName + " " + lastName;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    
    public ArrayList<Post> getPosts(){
        return posts;
    }
    public void setPosts(ArrayList<Post> posts){
        this.posts = posts;
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }
    public void setComments(ArrayList<Comment> comments){
        this.comments = comments;
    }

    public ArrayList<Post> getLikes(){
        return likes;
    }
    public void setLikes(ArrayList<Post> likes){
        this.likes = likes;
    }

    public ArrayList<User> getFriends(){
        return friends;
    }
    public void setFriends(ArrayList<User> friends){
        this.friends = friends;
    }
    
    //---------------------------------------------------------------------
    public ArrayList<Integer> getFriendsIDs(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (User friend : friends){
            ids.add(friend.getId());
        }
        return ids;
    }

 
}
