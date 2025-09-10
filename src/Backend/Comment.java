package Backend;
import java.time.LocalDateTime;

public class Comment {
     private int id;
    private String content;
    private User user;
    private LocalDateTime dateTime;

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
    public void setuser(User user) {
        this.user = user;
    }   
    
    public LocalDateTime getDateTime(){
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    } 
}
