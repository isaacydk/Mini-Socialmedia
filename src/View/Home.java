package View;

import java.time.LocalTime;
import Backend.User;

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
    public VBox mainCondition(Stage stage){
        Text t1 = new Text("This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. ");
        VBox total = new VBox(topBar(stage), displayTextArea(t1.getText()));
        total.setSpacing(10);
        total.setPadding(new Insets(10));
        return total;
    }
    
    private Region displayTextArea(String text) {
        Text messageText = new Text(text);
        messageText.getStyleClass().add("message-text");

        Hyperlink likeLink = new Hyperlink("👍");
        likeLink.getStyleClass().add("links-one");
        Hyperlink dislikeLink = new Hyperlink("👎");
        dislikeLink.getStyleClass().add("links-one");

        HBox leftBox = new HBox(8, likeLink, dislikeLink);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        Text userName = new Text("TempUser");
        userName.getStyleClass().add("text-one");

        String time = LocalTime.now().withNano(0).toString();
        Text timeText = new Text(time);
        timeText.getStyleClass().add("time-one");

        HBox rightBox = new HBox(timeText);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        HBox bottomBar = new HBox(leftBox, userName, rightBox);
        bottomBar.setSpacing(10);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        // Bubble container
        VBox bubble = new VBox(messageText, bottomBar);
        bubble.setSpacing(5);
        bubble.setPadding(new Insets(8));
        bubble.getStyleClass().add("border-one");

        // ✅ Bind wrapping width to bubble's parent width once it’s added
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
            Scene homeScene = new Scene(new Home().mainCondition(primaryStage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(homeScene);
        });

        post.setOnAction(e -> {
            Scene postScene = new Scene(new Post().mainCondition(primaryStage), 500, 400);
            postScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(postScene);
        });

        account.setOnAction(e -> {
            Scene accountScene = new Scene(new Account().mainCondition(primaryStage), 500, 400);
            accountScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(accountScene);
        });


        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");
        
        Text userInitial = new Text("TU");
        userInitial.getStyleClass().add("text");
        
        StackPane profilePic = new StackPane(profileCircle, userInitial);
        
        HBox total = new HBox(profilePic, home, post, account);
        total.setSpacing(10);
        total.getStyleClass().add("top-bar");
        total.setAlignment(Pos.TOP_LEFT);

        return total;
    }
}
