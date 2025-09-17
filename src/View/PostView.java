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
public class PostView {

    //line47 problem

    private User user;  // currently logged-in user

    public PostView(User user) {
        this.user = user;
    }

    public VBox buildPostBody(VBox Box) {

        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Write your post here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(5);
        inputArea.getStyleClass().add("temporary");

        Database db = new Database(); // your DB handler

        Button postBtn = new Button("Post");
        postBtn.setOnAction(e -> {
            String content = inputArea.getText().trim();
            if (!content.isEmpty() && user != null) {
                Post newPost = new Post(user.getId(), content, user.getUsername(), LocalDateTime.now());
                db.addPost(newPost);

                Box.getChildren().setAll(new Home(user).buildHomeBody());
                // Redirect back to Home after posting
                // Scene homeScene = new Scene(new Home(user).mainCondition(stage), 500, 400);//next line there is a problem
                // homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
                // stage.setScene(homeScene);
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
        // postBox.getStyleClass().add("border-one");

        return postBox;
    }
}
