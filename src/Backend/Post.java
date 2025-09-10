package Backend;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post{

    private int id;
    private String content;
    private User user;
    private LocalDateTime dateTime;
    private ArrayList<Comment> comments;
    private ArrayList<User> likes;

    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getContent(){ 
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    public ArrayList<Comment> getComments(){
        return comments;    
    }
    public void setComments(ArrayList<Comment> comments){
            this.comments = comments; 
    }

    public ArrayList<User>  getLikes(){
        return likes;
    }
    public void setLikes(ArrayList<User> likes){
        this.likes = likes;
    }
    

}
