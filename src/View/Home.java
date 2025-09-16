package View;

import java.time.LocalTime;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Home {
    public VBox buildHomeBody(){
        Text t1 = new Text("This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. ");
        VBox total = new VBox(displayTextArea(t1.getText()));
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

        bubble.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                messageText.wrappingWidthProperty()
                        .bind(newScene.widthProperty().subtract(60));
            }
        });


        return bubble;
    }
}
