package View;

import java.time.LocalTime;

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
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        Scene scene1 = new Scene(condition1(), 400, 300);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }
    private VBox condition1(){
        Text t1 = new Text("This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. This is a temporary paragraph. ");
        VBox total = new VBox(topBar(), displayTextArea(t1.getText()));
        // total.setAlignment(Pos.TOP_LEFT);
        total.setSpacing(10);
        total.setPadding(new Insets(10));
        total.getStylesheets().add(this.getClass().getResource("/Home.css").toExternalForm());
        return total;
    }
    private Region displayTextArea(String text) {
        Text messageText = new Text(text);
        messageText.setWrappingWidth(350); // wrap text neatly
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

        // Wrap message + bottom bar inside one styled VBox (the bubble)
        VBox bubble = new VBox(messageText, bottomBar);
        bubble.setSpacing(5);
        bubble.setPadding(new Insets(8));
        bubble.getStyleClass().add("border-one");

        return bubble;
    }

    private Region topBar(){
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");//setStyle("circle-define");
        Text userInitial = new Text("TU");
        userInitial.getStyleClass().add("text");
        StackPane profilePic = new StackPane(profileCircle, userInitial);
        HBox total = new HBox(profilePic, home, post, account);
        total.setSpacing(10);
        total.getStyleClass().add("top-bar");
        // total.setPadding(Insets.EMPTY); // Ensure no padding
        total.setAlignment(Pos.TOP_LEFT);

        return total;
    }
}
