package View;

import java.time.LocalDateTime;

import Backend.Database;
import Backend.Post;
import Backend.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PostView {

    private User user;

    public PostView(User user) {
        this.user = user;
    }

    public VBox buildPostBody(VBox Box) {

        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Write your post here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(5);
        inputArea.getStyleClass().add("temporary");

        Database db = new Database();

        Button postBtn = new Button("Post");
        postBtn.setOnAction(e -> {
            String content = inputArea.getText().trim();
            if (!content.isEmpty() && user != null) {
                Post newPost = new Post(user.getId(), content, user.getUsername(), LocalDateTime.now());
                db.addPost(newPost);

                Box.getChildren().setAll(new Home(user).buildHomeBody());
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> inputArea.clear());


        HBox buttonBar = new HBox(10, postBtn, cancelBtn);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        
        VBox postBox = new VBox();
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));

        postBox.getChildren().addAll(inputArea, buttonBar);

        return postBox;
    }
}
