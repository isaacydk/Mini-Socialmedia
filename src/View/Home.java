package View;

import java.time.LocalTime;
import Backend.User;
import java.util.List;
import Backend.Post;
import Backend.Database; 


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Home extends Application{

    User user; // Current logged-in user
        public Home() {};

    public Home(User user) {
        this.user = user; 
    }
    
    public void start(Stage primaryStage){
        Scene scene1 = new Scene(mainCondition(primaryStage), 500, 400);
        ThemeManager.applyTheme(scene1);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }
    
    public Region mainCondition(Stage stage) {
    VBox content = new VBox(topBar(stage));
    content.setSpacing(10);
    content.setPadding(new Insets(10));

    Database db = new Database();
    List<Post> posts = db.getAllPosts();

    for (Post post : posts) {
        content.getChildren().add(displayPost(post));
    }

    // Wrap content inside a ScrollPane
    javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane(content);
    scrollPane.setFitToWidth(true); // stretch VBox to width
    scrollPane.setPannable(true);   // allow mouse drag scrolling
    scrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);

    return scrollPane;
}

    
    private Region displayPost(Post post) {
        Database db = new Database(); 
    Text messageText = new Text(post.getContent());
    messageText.getStyleClass().add("message-text");

    int likeCount = db.getLikeCount(post.getId()); // ✅ fetch like count
    Text likeCountText = new Text(String.valueOf(likeCount));
    likeCountText.getStyleClass().add("text-one");

    Hyperlink likeLink = new Hyperlink("👍");
    likeLink.getStyleClass().add("links-one");
   

    likeLink.setOnAction(e -> {
        db.addLike(post.getId(), user.getId()); // TODO: replace 1 with actual user ID
        int updatedLikeCount = db.getLikeCount(post.getId());
        likeCountText.setText(String.valueOf(updatedLikeCount));
    });

       

    HBox leftBox = new HBox(8, likeLink, likeCountText);
    leftBox.setAlignment(Pos.CENTER_LEFT);

    Text userName = new Text(post.getUsername());
    userName.getStyleClass().add("text-one");

    Text timeText = new Text(post.getCreatedAt().toString());
    timeText.getStyleClass().add("time-one");

    HBox rightBox = new HBox(timeText);
    rightBox.setAlignment(Pos.CENTER_RIGHT);

    HBox bottomBar = new HBox(leftBox, userName, rightBox);
    bottomBar.setSpacing(10);
    bottomBar.setAlignment(Pos.CENTER_RIGHT);

    VBox bubble = new VBox(messageText, bottomBar);
    bubble.setSpacing(5);
    bubble.setPadding(new Insets(8));
    bubble.getStyleClass().add("border-one");

    bubble.parentProperty().addListener((obs, oldParent, newParent) -> {
        if (newParent != null) {
            messageText.wrappingWidthProperty()
                       .bind(((Region)newParent).widthProperty().subtract(40));
        }
    });

    return bubble;
}



    private Region topBar(Stage primaryStage){
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        home.setOnAction(e -> {
            Scene homeScene = new Scene(new Home(user).mainCondition(primaryStage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(homeScene);
        });

        post.setOnAction(e -> {
            Scene postScene = new Scene(new PostView(user).mainCondition(primaryStage), 500, 400);
            postScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(postScene);
        });

        account.setOnAction(e -> {
            Scene accountScene = new Scene(new Account(user).mainCondition(primaryStage), 500, 400);
            accountScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(accountScene);
        });


        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        String initials = getUserInitials();
        Text userInitial = new Text(initials);
        userInitial.getStyleClass().add("text");

        StackPane profilePic = new StackPane(profileCircle, userInitial);

        HBox total = new HBox(profilePic, home, post, account);
        total.setSpacing(10);
        total.getStyleClass().add("top-bar");
        total.setAlignment(Pos.TOP_LEFT);

        return total;
    }
    private String getUserInitials() {
        if (user == null) return "GU"; // Guest User
        String first = (user.getFirstName() != null && !user.getFirstName().isEmpty())
                ? user.getFirstName().substring(0, 1).toUpperCase() : "";
        String last = (user.getLastName() != null && !user.getLastName().isEmpty())
                ? user.getLastName().substring(0, 1).toUpperCase() : "";
        return first + last;
    }
}
