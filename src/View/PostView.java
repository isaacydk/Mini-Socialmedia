package View;

import java.time.LocalDateTime;

import Backend.Database;
import Backend.Post;
import Backend.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PostView extends Application {

    private User user;  // currently logged-in user
    private Database db = new Database(); // your DB handler

    public PostView() {} // no-arg constructor for JavaFX
    public PostView(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(mainCondition(primaryStage), 500, 400);
        ThemeManager.applyTheme(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Post");
        primaryStage.show();
    }

    public VBox mainCondition(Stage stage){
        VBox total = new VBox(topBar(stage), postArea(stage));
        total.setSpacing(10);
        total.setPadding(new Insets(10));
        return total;
    }

    private Region postArea(Stage stage) {
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Write your post here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(5);

        Button postButton = new Button("Post");
        Button cancelButton = new Button("Cancel");

        // 🔹 Save post to DB when clicked
        postButton.setOnAction(e -> {
            String content = inputArea.getText().trim();
            if (!content.isEmpty() && user != null) {
                Post newPost = new Post(user.getId(), content, user.getUsername(), LocalDateTime.now());
                db.addPost(newPost);

                // Redirect back to Home after posting
                Scene homeScene = new Scene(new Home(user).mainCondition(stage), 500, 400);
                homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
                stage.setScene(homeScene);
            }
        });

        cancelButton.setOnAction(e -> {
            // Go back to Home without posting
            Scene homeScene = new Scene(new Home(user).mainCondition(stage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            stage.setScene(homeScene);
        });

        HBox buttonBar = new HBox(10, postButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        VBox postBox = new VBox(inputArea, buttonBar);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));

        return postBox;
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
