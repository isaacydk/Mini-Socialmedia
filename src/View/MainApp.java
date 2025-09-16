package View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {
    private VBox contentBox; // placeholder for page body

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(10);

        // Top bar (constant)
        HBox topBar = createTopBar(primaryStage);

        // Content placeholder (start with Home)
        contentBox = new VBox();
        contentBox.getChildren().add(new Home().buildHomeBody());

        root.getChildren().addAll(topBar, contentBox);

        Scene scene = new Scene(root, 500, 400);
        ThemeManager.applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Social Media App");
        primaryStage.show();
    }

    private HBox createTopBar(Stage stage) {
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        // Navigation (just swap content)
        home.setOnAction(e -> contentBox.getChildren().setAll(new Home().buildHomeBody()));
        post.setOnAction(e -> contentBox.getChildren().setAll(new Post().buildPostBody()));
        account.setOnAction(e -> contentBox.getChildren().setAll(new Account().buildAccountBody()));

        // Profile icon
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        Text userInitial = new Text("TU");
        userInitial.getStyleClass().add("text");
        
        StackPane profilePic = new StackPane(profileCircle, userInitial);

        HBox topBar = new HBox(profilePic, home, post, account);
        topBar.setSpacing(10);
        topBar.getStyleClass().add("top-bar");
        topBar.setAlignment(Pos.TOP_LEFT);

        return topBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
