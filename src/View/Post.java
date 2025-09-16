package View;

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

public class Post extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        Scene scene = new Scene(mainCondition(primaryStage), 500, 400);
        ThemeManager.applyTheme(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Post");
        primaryStage.show();
    }
    
    public VBox mainCondition(Stage stage){
        VBox total = new VBox(topBar(stage), postArea());
        total.setSpacing(10);
        total.setPadding(new Insets(10));
        return total;
    }
    
    private Region postArea() {
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Write your post here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(5);
        inputArea.getStyleClass().add("temporary");

        Button postButton = new Button("Post");
        Button cancelButton = new Button("Cancel");

        HBox buttonBar = new HBox(10, postButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        VBox postBox = new VBox(inputArea, buttonBar);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));
        // postBox.getStyleClass().add("border-one"); // reuse same border style

        // Stretch to fit width
        // postBox.setMaxWidth(Double.MAX_VALUE);

        return postBox;
    }

    private Region topBar(Stage primaryStage){
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        home.setOnAction(e -> {
            Scene homeScene = new Scene(new Home().mainCondition(primaryStage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
            primaryStage.setScene(homeScene);
        });

        post.setOnAction(e -> {
            Scene postScene = new Scene(new Post().mainCondition(primaryStage), 500, 400);
            postScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
            primaryStage.setScene(postScene);
        });

        account.setOnAction(e -> {
            Scene accountScene = new Scene(new Account().mainCondition(primaryStage), 500, 400);
            accountScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
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
