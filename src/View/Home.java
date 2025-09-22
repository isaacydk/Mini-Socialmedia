package View;

import Backend.User;
import java.util.List;
import Backend.Post;
import Backend.Database; 


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Home {
    User user; 

    private Database db = new Database();

    public Home(User user) {
        this.user = user; 
    }
    public Region buildHomeBody(){
        VBox content = new VBox();
        List<Post> posts = db.getAllPosts();

        for (Post post : posts) {
            content.getChildren().add(displayTextArea(post));
            content.setSpacing(10);
        }

        content.setStyle("-fx-padding: 10; fx-background-color: transparent; -fx-background-insets: 0;");
        
        ScrollPane scroll = new ScrollPane(content);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scroll.getViewportBounds();
        scroll.setFitToWidth(true);
        
        return scroll;
    }
    private Region displayTextArea(Post post) {
        Text messageText = new Text(post.getContent());
        messageText.getStyleClass().add("message-text");

        int likeCount = db.getLikeCount(post.getId());
        Text likeCountText = new Text(String.valueOf(likeCount));
        likeCountText.getStyleClass().add("text-one");

        Hyperlink likeLink = new Hyperlink("👍");
        likeLink.getStyleClass().add("links-one");

        likeLink.setOnAction(e -> {
            db.addLike(post.getId(), user.getId());
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

        bubble.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                messageText.wrappingWidthProperty()
                        .bind(newScene.widthProperty().subtract(60));
            }
        });

        return bubble;
    }
}
