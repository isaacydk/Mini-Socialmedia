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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Home {
    User user; // Current logged-in user

    private Database db = new Database();

    public Home(User user) {
        this.user = user; 
    }
    public VBox buildHomeBody(){
        // Text t1 = new Text("This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. ");
        // VBox total = new VBox(displayTextArea(t1.getText()));
        // total.setSpacing(10);
        // total.setPadding(new Insets(10));
        // return total;
        VBox content = new VBox();
        List<Post> posts = db.getAllPosts();

        for (Post post : posts) {
            content.getChildren().add(displayTextArea(post));
        }

        // ScrollPane scroll = new ScrollPane(content);
        // scroll.setFitToWidth(true); // stretch VBox to width
        // scroll.setPannable(true);   // allow mouse drag scrolling
        // scroll.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);

        return content;
    }
    private Region displayTextArea(Post post) {
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

        // Bubble container
        VBox bubble = new VBox(messageText, bottomBar);
        bubble.setSpacing(5);
        bubble.setPadding(new Insets(8));
        bubble.getStyleClass().add("border-one");

        bubble.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                messageText.wrappingWidthProperty()
                        .bind(newScene.widthProperty().subtract(60));
            }
        });


        return bubble;
    }
}
