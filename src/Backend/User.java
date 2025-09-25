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
    private ArrayList<Post> likes;

    public User (){}
    public User(String fistName, String lastName, String username,  String email, String password){
    
        this.firstName = fistName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        
    }
 
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


    public ArrayList<Post> getLikes(){
        return likes;
    }
    public void setLikes(ArrayList<Post> likes){
        this.likes = likes;
    }
     
}
